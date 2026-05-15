package me.bitsandbites.backend.dtos;

import me.bitsandbites.backend.entities.Organisation;
import me.bitsandbites.backend.entities.Registered;
import java.util.List;

public class UserDTO {
    private Integer id;
    private String name;
    private Role role;
    private List<Organisation>  organisations;

    public UserDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDTO(Registered registered) {
        this.id = registered.getId();
        this.name = registered.getName();
    }

    public UserDTO(Integer id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public UserDTO(Integer id, String name, Role role, List<Organisation> organisations) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.organisations = organisations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
}
