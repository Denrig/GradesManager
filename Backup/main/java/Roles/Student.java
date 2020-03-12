package Roles;

import java.util.Objects;


public class Student extends User {
    private int grupa;
    private int an;

    public Student(int grupa, int an) {
        this.grupa = grupa;
        this.an = an;
    }

    public Student(Long entity, int grupa, int an) {
        super(entity);
        this.grupa = grupa;
        this.an = an;
    }

    public Student(Long id, Roles roles, Long entity, int grupa, int an) {
        super(id, roles, entity);
        this.grupa = grupa;
        this.an = an;
    }

    public Student(Long id, String nume, String roles, String user, String pass, Long entity, int grupa, int an) {
        super(id, nume, roles, user, pass, entity);
        this.grupa = grupa;
        this.an = an;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return grupa == student.grupa &&
                an == student.an;
    }

    @Override
    public int hashCode() {
        return Objects.hash(grupa, an);
    }

    @Override
    public String toString() {
        return "Student{" +
                "grupa=" + grupa +
                ", an=" + an +
                ", roles=" + roles +
                '}';
    }
}
