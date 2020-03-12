package Validator;

import Entity.Semester;
import Exceptions.ValidationException;

public class SemesterValidator implements Validator<Semester> {
    @Override
    public void validate(Semester entity) throws ValidationException {
        String errMsg = null;
        if (entity.getStartSemester().isAfter(entity.getEndSemester()))
            errMsg = "Datile semestrului invalide";
        if (entity.getBeginHolyday().isAfter(entity.getEndHolyday()))
            errMsg = "Datile semestrului invalide";
        if (errMsg != null)
            throw new ValidationException(errMsg);
    }
}
