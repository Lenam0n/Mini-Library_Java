package entities;

import interfaces.IEntity;
import model.IMemberModel;

public class Member implements IEntity<Long>, IMemberModel {
    private Long id;
    private String name;
    private String email;

    public Member() { /* für JSON‐Deserialisierung benötigt */ }

    public Member(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("Member[id=%d, name=%s, email=%s]", id, name, email);
    }
}
