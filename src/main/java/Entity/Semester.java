package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class Semester extends Entity<Long> {
    int year;
    int semester;
    private LocalDate startSemester;
    private LocalDate beginHolyday;
    private LocalDate endHolyday;
    private LocalDate endSemester;

    public Semester(int year, int semester, LocalDate startSemester, LocalDate beginHolyday, LocalDate endHolyday, LocalDate endSemester) {
        this.year = year;
        this.semester = semester;
        this.startSemester = startSemester;
        this.beginHolyday = beginHolyday;
        this.endHolyday = endHolyday;
        this.endSemester = endSemester;
    }

    public Semester(Long id, int year, int semester, LocalDate startSemester, LocalDate beginHolyday, LocalDate endHolyday, LocalDate endSemester) {
        setId(id);
        this.year = year;
        this.semester = semester;
        this.startSemester = startSemester;
        this.beginHolyday = beginHolyday;
        this.endHolyday = endHolyday;
        this.endSemester = endSemester;
    }

    public Semester() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Semester)) return false;
        Semester semester1 = (Semester) o;
        return year == semester1.year &&
                semester == semester1.semester &&
                Objects.equals(startSemester, semester1.startSemester) &&
                Objects.equals(beginHolyday, semester1.beginHolyday) &&
                Objects.equals(endHolyday, semester1.endHolyday) &&
                Objects.equals(endSemester, semester1.endSemester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, semester, startSemester, beginHolyday, endHolyday, endSemester);
    }

    public int getyear() {
        return year;
    }

    public void setyear(int year) {
        this.year = year;
    }

    public int getsemester() {
        return semester;
    }

    public void setsemester(int semester) {
        this.semester = semester;
    }

    public LocalDate getStartSemester() {
        return startSemester;
    }

    public void setStartSemester(LocalDate startSemester) {
        this.startSemester = startSemester;
    }

    public LocalDate getBeginHolyday() {
        return beginHolyday;
    }

    public void setBeginHolyday(LocalDate beginHolyday) {
        this.beginHolyday = beginHolyday;
    }

    public LocalDate getEndHolyday() {
        return endHolyday;
    }

    @Override
    public String toString() {
        return "Year=" + year + " Semester=" + semester ;
    }

    public void setEndHolyday(LocalDate endHolyday) {
        this.endHolyday = endHolyday;
    }

    public LocalDate getEndSemester() {
        return endSemester;
    }

    public void setEndSemester(LocalDate endSemester) {
        this.endSemester = endSemester;
    }

    public LocalDate getCurrentWeek() {
        return LocalDate.now();
    }

}