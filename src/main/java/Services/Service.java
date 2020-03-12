package Services;

import Entity.Entity;

public interface Service<T extends Entity<Long>> {

    /**
     * @param t Entity
     * @return true if its added,false otherwise
     */
    boolean add(T t);

    /**
     * @param id Long type
     * @return true if the item is deleted,false otherwise
     */
    boolean delete(Long id);

    /**
     * @param t entity type
     * @return true if the item is updated,false otherwise
     */
    boolean update(T t);

    /**
     * @param id long
     * @return Entity type
     */
    T findOne(Long id);

    /**
     * @return Iterator for the list of objects
     */
    Iterable<T> findAll();
    /**
     * @return true if its empty, false...
     */
    boolean isEmpty();

}
