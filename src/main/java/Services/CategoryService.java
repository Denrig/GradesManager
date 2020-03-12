package Services;

import Entity.Category;
import Entity.Entity;
import Exceptions.ValidationException;
import Repository.CategoryRepository;
import Entity.Student;
import Validator.CategoryValidator;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

public class CategoryService extends EntityService<Category> {

    public CategoryService(Long disciplina,Properties props) {
        this.repository = new CategoryRepository(new CategoryValidator(),props,disciplina);
    }

    public Long getDisciplina(){return ((CategoryRepository)repository).getDisciplina();}

    public void setDisciplina(Long disciplina) {((CategoryRepository)repository).setDisciplina(disciplina);}


    @Override
    public boolean add(Category entity) throws ValidationException {
        validate(entity);
        return super.add(entity);
    }

    public int validate(Category category)throws ValidationException {
        AtomicInteger sumPercent= new AtomicInteger();
        repository.findAll().forEach(x-> sumPercent.addAndGet(x.getPercentage()));
        sumPercent.set(sumPercent.get()+category.getPercentage());
        if(sumPercent.get()<0||sumPercent.get()>100)
            throw new ValidationException("Procentul depaseste pragul.Suma este "+sumPercent.get()+"%");
        return sumPercent.get();

    }
}
