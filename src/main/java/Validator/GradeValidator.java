package Validator;

import Entity.Grade;
import Exceptions.ValidationException;

import java.time.LocalDate;

public class GradeValidator implements Validator<Grade> {
    @Override
    public void validate(Grade entity) throws ValidationException {
        String err = null;
        if (entity.getGrade() < 1 || entity.getGrade() > 10)
            err = "Nota trebuie sa fie in intervalul [1,10]";
        if(entity.getUploaded().isAfter(LocalDate.now()))
            err="Data incarcarii nu poate fii dupa ziua de azi";
        if (err != null)
            throw new ValidationException(err);
    }
}
