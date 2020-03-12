package Services;

import Entity.Grade;
import Exceptions.ValidationException;
import Repository.GradeRepository;
import Entity.Student;
import Repository.StudentRepository;
import Validator.GradeValidator;

import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

public class GradeService extends EntityService<Grade> {

    public GradeService(Properties props,Long disciplina) {
        this.repository = new GradeRepository(new GradeValidator(),props,disciplina);
    }

    public void setDisciplina(Long disciplina){
        ((GradeRepository)repository).setDisciplina(disciplina);
    }

    public Long getDisciplina(){
        return  ((GradeRepository)repository).getDisciplina();
    }

    public double getFinalGrade(Student student, Long disciplina)  {
        CategoryService categoryRepository = new CategoryService(disciplina,repository.getProps());
        AtomicReference<Double> media = new AtomicReference<>(0d);
        StreamSupport.stream(findAll().spliterator(), false).filter(grade ->grade.getStudentID()==student.getId()).forEach(grade -> {
            media.set(media.get()+(grade.getGrade()*categoryRepository.findOne(grade.getCategory()).getPercentage())/100d);
        });
        return media.get();
    }

    public void validate(Grade grade)throws ValidationException {
        if(StreamSupport.stream(repository.findAll().spliterator(),false).filter(x->grade.getCategory()==x.getCategory()&&x.getStudentID()==grade.getStudentID()).findFirst().orElse(null)!=null)
            throw new ValidationException("Studentul selectat are deja nota la aceasta categorie");
    }

}
