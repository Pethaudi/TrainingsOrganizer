package me.bitsandbites.backend.dtos;

import me.bitsandbites.backend.entities.Registered;
import org.apache.catalina.User;

public class UserDTO {
    private Integer id;
    private String name;
    private Role role;

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
}
