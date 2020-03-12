package Services;

import Entity.Assignment;
import Entity.TemaGrade;
import Exceptions.ValidationException;
import Validator.GradeValidator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.atomic.AtomicBoolean;


public class TemaGradeService extends EntityService<TemaGrade> {

    public TemaGradeService(Long disciplina) {

        this.repository = new TemaGradeRepository(new GradeValidator(), disciplina);
    }


    /**
     * Adds an grade to a student with some conditions
     *
     * @param grade
     * @return true-succes
     * false-fail
     */
    @Override
    public boolean add(TemaGrade grade) {
        Assignment temp = new TemeService(1l).findOne(grade.getTemaId());
        try {
            grade.setId(repository.getLastID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (exists(grade) == false) {
            if (grade.isDepunctare()) {
                if (getDays(temp.getEndWeek(), grade.getIncarcataIn()) > 14 && temp.getEndWeek().isBefore(grade.getIncarcataIn())) {
                    try {
                        if (repository.save(grade) == null) {

                            return true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (temp.isActive()) {
                        TemaGrade grade1 = grade;
                        grade1.setNota(modifyGrade(grade, temp));
                        try {
                            if (repository.save(grade1) == null) {
                                return true;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    throw new ValidationException("Tema Invalida,nu se mai pot salva note");
                }
            }
            try {
                if (repository.save(grade) == null) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    private int getDays(LocalDate l1, LocalDate l2) {
        Period period = Period.between(l2, l1);

        return Math.abs(period.getDays());
    }

    private double modifyGrade(TemaGrade grade, Assignment assignment) {
        if (assignment.getEndWeek().isBefore(grade.getPredataIn())) {
            if (getDays(assignment.getEndWeek(), grade.getPredataIn()) > 0 && getDays(assignment.getEndWeek(), grade.getPredataIn()) < 7)
                return grade.getNota() - 1;
            if (getDays(assignment.getEndWeek(), grade.getPredataIn()) > 7 && getDays(assignment.getEndWeek(), grade.getPredataIn()) < 15)
                return grade.getNota() - 2;
        }
        return grade.getNota();
    }

    private boolean exists(TemaGrade grade) {
        AtomicBoolean exists = new AtomicBoolean(false);
        try {
            repository.findAll().forEach(x -> {
                if (grade.getTemaId() == x.getTemaId() && grade.getCategory() == x.getCategory())
                    exists.set(true);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists.get();

    }


}
