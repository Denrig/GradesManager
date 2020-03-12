package Entity;

import java.time.LocalDate;
import java.util.Objects;


public class Grade extends Entity<Long> {
    private Long studentID;
    private Long category;
    private double grade;
    private LocalDate uploaded;

    public Grade(Long id, Long studentID, Long category, double grade, LocalDate uploaded) {
        setId(id);
        this.studentID = studentID;
        this.category = category;
        this.grade = grade;
        this.uploaded = uploaded;

    }

    public Grade(Long studentID, Long category, double grade, LocalDate uploaded) {
        this.studentID = studentID;
        this.category = category;
        this.grade = grade;
        this.uploaded = uploaded;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public LocalDate getUploaded() {
        return uploaded;
    }

    public void setUploaded(LocalDate uploaded) {
        this.uploaded = uploaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grade)) return false;
        Grade grade1 = (Grade) o;
        return Double.compare(grade1.grade, grade) == 0 &&
                Objects.equals(studentID, grade1.studentID) &&
                Objects.equals(category, grade1.category) &&
                Objects.equals(uploaded, grade1.uploaded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID, category, grade, uploaded);
    }

    @Override
    public String toString() {
        return "Grade{" + getId() +
                "  studentID=" + studentID +
                ", category=" + category +
                ", grade=" + grade +
                ", uploaded=" + uploaded +
                '}';
    }


}
