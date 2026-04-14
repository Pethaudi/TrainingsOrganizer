package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Integer> {
}
