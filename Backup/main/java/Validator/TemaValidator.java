package Validator;

import Entity.Assignment;
import Entity.Semester;
import Exceptions.ValidationException;
import Services.SemesterService;

public class TemaValidator implements Validator<Assignment> {


    public TemaValidator() {

    }

    /**
     *
     * @param entity-Entity
     * @throws ValidationException
     * if the data is invalid
     */
    @Override
    public void validate(Assignment entity) throws ValidationException {

        Semester semester = new SemesterService().currentSemester();
        String msg=null;
        if (entity.getStartWeek().isAfter(entity.getEndWeek()))
            msg="Date Invalide:Dead Line";
        if (entity.getStartWeek().isAfter(semester.getBeginHolyday()) && entity.getStartWeek().isBefore(semester.getEndHolyday()))
            msg = "Its holyday you monster";
        if (entity.getEndWeek().isAfter(semester.getEndSemester()))
            msg = "School is out for summer";
        if (entity.getStartWeek().isBefore(semester.getStartSemester()))
            msg = "Really?";
        if(msg!=null)
        throw new ValidationException(msg);

    }
}
