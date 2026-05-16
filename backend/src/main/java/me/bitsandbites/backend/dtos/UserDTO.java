package me.bitsandbites.backend.dtos;

import me.bitsandbites.backend.entities.MemberOfOrganisation;
import me.bitsandbites.backend.entities.Registered;
import java.util.List;

public class UserDTO {
    private Integer id;
    private String name;
    private List<MemberOfOrganisation> memberOfOrganisations;

    public UserDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDTO(Registered registered) {
        this.id = registered.getId();
        this.name = registered.getName();
    }

    public UserDTO(Integer id, String name, List<MemberOfOrganisation> memberOfOrganisations) {
        this.id = id;
        this.name = name;
        this.memberOfOrganisations = memberOfOrganisations;
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

    public List<MemberOfOrganisation> getMemberOfOrganisations() {
        return memberOfOrganisations;
    }

    public void setMemberOfOrganisations(List<MemberOfOrganisation> memberOfOrganisations) {
        this.memberOfOrganisations = memberOfOrganisations;
    }
}
