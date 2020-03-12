package Entity;

import java.util.Objects;


public class Discipline extends Entity<Long> {
    private String profesor;
    private Long semester;
    private String name;

    public Discipline(Long id, String profesor, Long semester, String name) {
        setId(id);
        this.profesor = profesor;
        this.semester = semester;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "profesor='" + profesor + '\'' +
                ", semester=" + semester +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Discipline)) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(profesor, that.profesor) &&
                Objects.equals(semester, that.semester) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profesor, semester, name);
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }
}
