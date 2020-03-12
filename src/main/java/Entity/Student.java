package Entity;

import java.util.Objects;


public class Student extends Entity<Long> {
    private int grupa;
    private int an;
    private String email;
    private String nume;

    public Student(Long id,int grupa, int an, String email, String nume) {
        setId(id);
        this.grupa = grupa;
        this.an = an;
        this.email = email;
        this.nume = nume;
    }

    @Override
    public String toString() {
        return nume ;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return grupa == student.grupa &&
                an == student.an &&
                Objects.equals(email, student.email) &&
                Objects.equals(nume, student.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grupa, an, email, nume);
    }
}
