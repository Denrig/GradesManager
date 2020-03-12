package Services;

import Entity.Category;
import Entity.Student;
import Repository.CategoryRepository;
import Validator.CategoryValidator;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

public class CategoryService extends EntityService<Category> {

    public CategoryService(Long disciplina) {
        this.repository = new CategoryRepository(new CategoryValidator(), disciplina);
    }

    public double getMedia(Student student, Long disciplina, Long category) {
        GradeService service = new GradeService(disciplina);
        AtomicReference<Double> media = new AtomicReference<>(0d);
        AtomicInteger count = new AtomicInteger();
        StreamSupport.stream(service.findAll().spliterator(), false).filter(grade -> grade.getStudentID() == student.getId()
                && grade.getCategory() == category).forEach(grade -> {
            media.set(media.get() + grade.getNota());
            count.getAndIncrement();
        });

        media.set(media.get() / count.get());
        media.set((findOne(category).getProcent() / 100d) * media.get());
        return media.get();
    }
}
