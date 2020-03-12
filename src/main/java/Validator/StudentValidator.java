package Validator;


import Exceptions.ValidationException;
import Entity.Student;

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

        if(entity.getAn()<1||entity.getAn()>3)
            throw new ValidationException("Un student are maxim 3 ani de studiu si minim 1");
        if(entity.getGrupa()<100||entity.getGrupa()>999)
            throw new ValidationException("Grupele sunt intre [100,999]");
        if(!isValid(entity.getEmail()))
            throw new ValidationException("Emailul invalid");

    }
}
