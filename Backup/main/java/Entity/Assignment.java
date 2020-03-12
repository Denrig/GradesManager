package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class Assignment extends Entity<Long> {
    private String title;
    private LocalDate startWeek = LocalDate.now();
    private LocalDate endWeek;
    private boolean active;
    private Difficulty difficulty;

    public Assignment() {
    }

    public Assignment(String title, LocalDate startWeek, LocalDate endWeek, boolean active, String difficulty) {
        this.title = title;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.active = active;
        this.difficulty = Difficulty.valueOf(difficulty);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Assignment(Long id, String title, LocalDate startWeek, LocalDate endWeek, boolean active, String difficulty) {
        setId(id);
        this.title = title;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.active = active;
        this.difficulty = Difficulty.valueOf(difficulty);
    }

    /**
     * @param o
     * @return bool
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment assignment = (Assignment) o;
        return startWeek == assignment.startWeek &&
                endWeek == assignment.endWeek &&
                active == assignment.active &&

                difficulty == assignment.difficulty;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(startWeek, endWeek, active, difficulty);
    }

    /**
     * @return string of the homework
     */
    @Override
    public String toString() {
        return "ID:" + getId() + " Tema[" + " Start:" + startWeek + " End:" + endWeek + " IsActive:" + active + " Dificulty" + difficulty + "]" + "\n";
    }

    /**
     * @return file string format of the homework
     */
    public String toFileString() {
        return getId() + ";" + startWeek + ";" + endWeek + ";" + active + ";" + difficulty;
    }


    /**
     * @return int
     */
    public LocalDate getStartWeek() {
        return startWeek;
    }

    /**
     * @param startWeek-int
     */
    public void setStartWeek(LocalDate startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * @return int
     */
    public LocalDate getEndWeek() {
        return endWeek;
    }

    /**
     * @return true if its active false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active-bool
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return Dificulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty-Dificulty
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @param endWeek-int
     */
    public void setEndWeek(LocalDate endWeek) {
        this.endWeek = endWeek;
    }

}
