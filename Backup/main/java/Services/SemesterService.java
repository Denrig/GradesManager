package Services;

import Entity.Semester;
import Repository.SemesterRepository;
import Validator.SemesterValidator;

import java.time.LocalDate;

public class SemesterService extends EntityService<Semester> {

    public SemesterService() {

        this.repository = new SemesterRepository(new SemesterValidator());
    }

    /**
     * Finds the current semester
     * @return Semester
     * cureent semester
     *
     */
    public Semester currentSemester() {
        for (Semester semester : findAll())
            if (semester.getStartSemester().isBefore(LocalDate.now()) && semester.getEndSemester().isAfter(LocalDate.now()))
                return semester;
        return null;
    }


}
