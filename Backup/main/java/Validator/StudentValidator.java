package Validator;

import Entity.Student;
import Exceptions.ValidationException;

public class StudentValidator implements Validator<Student> {
    private String errMsg;

    /**
     *
     * @param email-string
     * @return-true if its valid false otherwise
     */
    private static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    /**
     *
     * @param entity-Entity
     * @throws ValidationException
     * if the data is invalid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if(entity.getNume().matches(".*\\d.*")||entity.getPrenume().matches(".*\\d.*")||entity.getProfesor().matches(".*\\d.*"))
            errMsg="Date Invalide:Nume,Prenume or Professor";
        if(entity.getAn()<1||entity.getAn()>3)
            throw new ValidationException("Date Invalide:An");
        if(entity.getGrupa()<100||entity.getGrupa()>999)
            throw new ValidationException("Date Invalide:Gurpa");
        if(!isValid(entity.getEmail()))
            throw new ValidationException("Date Invalide:Email");
        if(entity.getMediaAnual()<1||entity.getMediaAnual()>10)
            throw new ValidationException("Date Invalide:Media");
    }
}
