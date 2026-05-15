package me.bitsandbites.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "organisations")
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String address;

    public Organisation() {}

    public Organisation(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
            "Organisation[id=%d, name=%s]",
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

    public String getAddress() {
        return this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        }

        final Organisation other = (Organisation) o;
        return Objects.equals(this.id, other.getId());
    }
}