package me.bitsandbites.backend.entities;

import me.bitsandbites.backend.dtos.Role;

public interface MemberOfOrganisationView {
    Integer getId();
    Organisation getOrganisation();
    Role getRole();
}
