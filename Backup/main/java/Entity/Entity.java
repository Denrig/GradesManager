package Entity;

/**
 *
 * @param <ID>
 */
public abstract class Entity<ID> {
    private ID id;

    /**
     *
     * @return id
     */
    public ID getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(ID id) {
        this.id = id;
    }


}
