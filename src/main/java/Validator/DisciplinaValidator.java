package Validator;

import Entity.Discipline;
import Exceptions.ValidationException;

public class DisciplinaValidator implements Validator<Discipline> {
    @Override
    public void validate(Discipline entity) throws ValidationException {
        if(entity.getName().contains("//w"))
            throw new ValidationException("Numele trebuie sa contina doar litere");
    }
}
