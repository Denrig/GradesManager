package Validator;

import Entity.Category;
import Exceptions.ValidationException;

public class CategoryValidator implements Validator<Category> {

    @Override
    public void validate(Category entity) throws ValidationException {
        if (entity.getPercentage() > 100 || entity.getPercentage() < 0)
            throw new ValidationException("Procentul trebuie sa fie in intervalul [0,100]");
    }


}
