package Entity;

import java.util.Objects;


public class Discipline extends Entity<Long> {
    private Long semester;
    private String name;

    public Discipline(Long id, Long semester, String name) {
        setId(id);
        this.semester = semester;
        this.name = name;
    }

    public Discipline(Long semester, String name) {
        this.semester = semester;
        this.name = name;
    }

    @Override
    public String toString() {
        return name ;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Discipline)) return false;
        Discipline that = (Discipline) o;
        return
                Objects.equals(semester, that.semester) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, name);
    }





    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
