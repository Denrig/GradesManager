package Services;

import Entity.Grade;
import Entity.Student;
import Repository.NotaRepository;
import Validator.GradeValidator;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

public class GradeService extends EntityService<Grade> {

    public GradeService(Long disciplina) {
        this.repository = new NotaRepository(new GradeValidator(), disciplina);
    }


    public double getFinalGrade(Student student, Long disciplina) throws SQLException {
        CategoryService categoryRepository = new CategoryService(disciplina);
        AtomicReference<Double> media = new AtomicReference<>(0d);
        StreamSupport.stream(categoryRepository.findAll().spliterator(), false).forEach(id -> {
            double dl = categoryRepository.getMedia(student, disciplina, id.getId());
            if (!Double.isNaN(dl))
                media.set(dl);
        });
        return media.get();
    }

}
