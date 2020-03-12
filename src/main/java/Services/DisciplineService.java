package Services;

import Entity.Discipline;
import Exceptions.ValidationException;
import GUI.DisciplineController;
import Repository.DisciplinaRepository;
import Validator.DisciplinaValidator;

import java.util.Properties;
import java.util.stream.StreamSupport;

public class DisciplineService extends EntityService<Discipline> {
    public DisciplineService(Properties props){
        this.repository=new DisciplinaRepository(new DisciplinaValidator(),props);
    }

    public void validate(Discipline discipline)throws ValidationException {
       if(StreamSupport.stream(repository.findAll().spliterator(),false).filter(x->x.getName().equals(discipline.getName())).findFirst().orElse(null)!=null)
                throw new ValidationException("Numele disciplinei este deja luat");
    }
}
