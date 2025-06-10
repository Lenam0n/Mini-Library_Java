// Datei: global/Repository.java
package global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IEntity;
import interfaces.Validatable;
import interfaces.IRepository;
import interfaces.RepositoryPrintService;

/**
 * Einfache In-Memory-Implementierung von IRepository.
 *
 * @param <T>  Entity-Typ, der IEntity<ID> & Validatable<T,?> implementiert.
 * @param <ID> Typ der ID (z.B. String für ISBN, Long für Member-ID).
 */
public class Repository<
        T extends IEntity<ID> & Validatable<T, ?>,
        ID
    > implements IRepository<T, ID>  {

    private final Map<ID, T> storage = new HashMap<>();

    @Override
    public void save(T entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void deleteById(ID id) {
        storage.remove(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }
}
