package Validator;

import Entity.Grade;
import Exceptions.ValidationException;

public class GradeValidator implements Validator<Grade> {
    @Override
    public void validate(Grade entity) throws ValidationException {
        String err = null;
        if (entity.getGrade() < 1 || entity.getGrade() > 10)
            err = "Nota Invalida";
//        if (entity.getPredataIn().isAfter(entity.getIncarcataIn()))
//            err = "Date invalide";
        if (err != null)
            throw new ValidationException(err);
    }
}
