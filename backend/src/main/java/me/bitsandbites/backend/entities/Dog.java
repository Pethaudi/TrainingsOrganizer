package me.bitsandbites.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "dogs")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;

    public Dog() {}

    public Dog(Integer id) {
        this.id = id;
    }

    public Dog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Dog[id=%d, name=%s]",
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
