package repository;

import java.io.IOException;

public interface ICrudRepository<E> {
    /**
     * @param entity -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    E findOne(E entity) throws IOException;

    /**
     * @return all entities
     */
    Iterable<E> findAll() throws IOException;

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    E save(E entity) throws IOException;

    /**
     * removes the entity with the specified id
     *
     * @param entity id must be not null
     * @return the removed entity or null if there is no entity with the given id
     */
    E delete(E entity) throws IOException;

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */
    E update(E entity) throws IOException;
}
