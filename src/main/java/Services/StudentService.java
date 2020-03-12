package Services;


import Entity.Student;
import Repository.StudentRepository;
import Validator.StudentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StudentService extends EntityService<Student> {
    public StudentService(Properties props,Long disciplina) {
        this.repository =new StudentRepository(new StudentValidator(), props,disciplina);
    }

    public void setDisciplina(Long disciplina){
        ((StudentRepository)repository).setDisciplina(disciplina);
    }

    public Long getDisciplina(){
       return  ((StudentRepository)repository).getDisciplina();
    }

    public List<Student> filter(String grupa, String an, String nume){
        Predicate<Student> byGrupa=x->{
                if(!grupa.equals(""))
                    return Integer.toString(x.getGrupa()).contains(grupa);
                return true;
        };
        Predicate<Student> byAn=x->{
            if(!an.equals(""))
                return Integer.toString(x.getAn()).contains(an);
            return true;
        };
        Predicate<Student> byNume=x->{
            if(!nume.equals(""))
                return x.getNume().contains(nume);
            return true;
        };
        return StreamSupport.stream(findAll().spliterator(),false).filter(byAn.and(byGrupa).and(byNume)).collect(Collectors.toList());
    }

}
