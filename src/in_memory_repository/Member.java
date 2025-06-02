package in_memory_repository;

import in_memory_repository.interfaces.IEntity;

public class Member implements IEntity<Long> {
	private Long id;
	private String name;
	private String email;
	
	public Member(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Long getId() { return this.id; }

    @Override
    public String toString() {
        return String.format("Member[id=%d, name=%s, email=%s]", id, name, email);
    }
}
