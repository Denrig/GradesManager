package Validator;

import Exceptions.ValidationException;

/**
 *
 * @param <E>-entity
 */
public interface Validator<E> {
    /**
     *
     * @param entity-Entity
     * @throws ValidationException
     * if the data is invalid
     */
    void validate(E entity) throws ValidationException;
}

