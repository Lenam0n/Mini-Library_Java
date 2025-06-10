// Datei: entities/Member.java
package entities;

import interfaces.IEntity;
import interfaces.validators.MemberValidator;
import global.Result;
import model.IMemberModel;
import utils.Validatable;

public class Member implements IEntity<Long>, IMemberModel, Validatable<Member, MemberValidator> {
    private Long id;
    private String name;
    private String email;

    public Member() { /* für JSON‐Deserialisierung benötigt */ }

    public Member(Long id, String name, String email) {
        this.id    = id;
        this.name  = name;
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

    // liefert den Singleton-Validator zurück
    @Override
    public MemberValidator getValidator() {
        return MemberValidator.INSTANCE;
    }

    // optional: delegiert auf das Default-Validate der Validatable-Schnittstelle
    @Override
    public Result<Member, RuntimeException> validate() {
        return Validatable.super.validate();
    }
}
