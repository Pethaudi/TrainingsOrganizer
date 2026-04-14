package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.DogTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogTeamRepository extends JpaRepository<DogTeam, Integer> {
}
