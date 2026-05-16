package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.MemberOfOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersOfOrganisationRepository extends JpaRepository<MemberOfOrganisation, Integer> {
    Iterable<MemberOfOrganisation> findByRegisteredId(Integer id);
    <T> Iterable<T> findByRegisteredId(Integer id, Class<T> type);
}
