package me.bitsandbites.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Registered {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String address;

    // https://www.baeldung.com/jpa-many-to-many
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "membersoforganisation",
        joinColumns = @JoinColumn(name = "registeredid"),
        inverseJoinColumns = @JoinColumn(name = "organisationid")
    )
    private List<Organisation> organisations;

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

    public String getAddress() {
        return this.address;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
}
