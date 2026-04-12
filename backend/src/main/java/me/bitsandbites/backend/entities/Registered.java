package me.bitsandbites.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Registered {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;

    public Registered() {}

    public Registered(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
            "Registered[id=%d, name=%s]",
            this.id,
            this.name
        );
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
