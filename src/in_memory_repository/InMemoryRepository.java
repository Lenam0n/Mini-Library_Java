package in_memory_repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in_memory_repository.interfaces.IEntity;
import in_memory_repository.interfaces.ISearchableRepository;

public class InMemoryRepository<T extends IEntity<ID>, ID> implements ISearchableRepository<T, ID> {
	private final Map<ID,T> storage;
	
	public InMemoryRepository() {
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
