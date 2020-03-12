package Services;

import Entity.Student;
import Repository.StudentRepository;
import Validator.StudentValidator;



public class StudentService extends EntityService<Student> {
    public StudentService(Long disciplina) {
        this.repository = new StudentRepository(new StudentValidator(), disciplina);
    }
}
