package me.bitsandbites.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "dogteams")
public class DogTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "handlerid", referencedColumnName = "id")
    private Registered handler;
    @ManyToOne()
    @JoinColumn(name = "dogid", referencedColumnName = "id")
    private Dog dog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Registered getHandler() {
        return handler;
    }

    public void setHandler(Registered handler) {
        this.handler = handler;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
