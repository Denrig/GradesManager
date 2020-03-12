package Entity;

import java.util.Objects;

public class Category extends Entity<Long> {
    private String name;
    private int percentage;
    private Long disciplinaID;

    public Category(String name, int percentage, Long disciplinaID) {
        this.name = name;
        this.percentage = percentage;
        this.disciplinaID = disciplinaID;
    }

    public Category(Long id, String name, int percentage, Long disciplinaID) {
        setId(id);
        this.name = name;
        this.percentage = percentage;
        this.disciplinaID = disciplinaID;
    }

    public Long getDisciplinaID() {
        return disciplinaID;
    }

    public void setDisciplinaID(Long disciplinaID) {
        this.disciplinaID = disciplinaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return percentage == category.percentage &&
                Objects.equals(name, category.name) &&
                Objects.equals(disciplinaID, category.disciplinaID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, percentage, disciplinaID);
    }

    @Override
    public String toString() {
        return name;
    }
}