package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationsRepository extends JpaRepository<Organisation, Integer> {
}
