package interfaces;

import java.util.List;

/**
 * Basis‐Interface für reine CRUD‐Operationen ohne findById.
 */
public interface ICrudRepository<T extends IEntity<ID>, ID> {
    void save(T entity);
    void deleteById(ID id);
    List<T> findAll();
}
