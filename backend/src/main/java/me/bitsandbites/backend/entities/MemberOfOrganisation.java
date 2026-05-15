package me.bitsandbites.backend.entities;

import jakarta.persistence.*;
import me.bitsandbites.backend.dtos.Role;

@Entity
@Table(name = "membersoforganisation")
public class MemberOfOrganisation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "registeredid")
    private Registered registered;
    @ManyToOne
    @JoinColumn(name = "organisationid")
    private Organisation organisation;
    @Enumerated(EnumType.STRING)
    private Role role;

    public MemberOfOrganisation() {
    }

    public MemberOfOrganisation(Integer id, Registered registered, Organisation organisation, Role role) {
        this.id = id;
        this.registered = registered;
        this.organisation = organisation;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
