package Services;

import Entity.Entity;
import Repository.DBRepository;

import java.sql.SQLException;



public abstract class EntityService<T extends Entity<Long>> implements Service<T> {
    protected DBRepository<T> repository = null;
    /**
     * Adds an entity to the list and it gives an available id
     *
     * @param entity
     * @return true-added
     * false-not
     */
    @Override
    public boolean add(T entity) {
        try {
            entity.setId(repository.getLastID());
            if (repository.save(entity) == null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * Deletes an entity and makes the id available
     *
     * @param entity
     * @return true -deleted
     * false-not
     */
    @Override
    public boolean delete(Long entity) {
        try {
            if (repository.delete(entity) != null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an entity
     *
     * @param entity
     * @return true-succes
     * false-fail
     */
    @Override
    public boolean update(T entity) {
        try {
            if (repository.update(entity) == null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * finds an entity
     *
     * @param id long
     * @return entity with the id
     */
    @Override
    public T findOne(Long id) {
        try {
            return repository.findOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return iterable
     * the list of entitys
     */
    @Override
    public Iterable<T> findAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * finds if the list is empty
     * @return
     */
    @Override
    public boolean isEmpty() {
        try {
            if (repository.findAll().spliterator().estimateSize() == 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
