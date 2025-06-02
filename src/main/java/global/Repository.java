package global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IEntity;
import interfaces.ISearchableRepository;

public class Repository<T extends IEntity<ID>, ID> implements ISearchableRepository<T, ID> {
	private final Map<ID,T> storage;
	
	public Repository() {
		this.storage = new HashMap<>();
	}

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
