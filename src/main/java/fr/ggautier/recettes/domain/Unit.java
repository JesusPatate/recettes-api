package fr.ggautier.recettes.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unit")
@SuppressWarnings("WeakerAccess")
public class Unit {

    @Id
    Integer id;

    String name;

    public Integer getId() {
        return this.id;
    }

    protected void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    protected void setName(final String name) {
        this.name = name;
    }
}
