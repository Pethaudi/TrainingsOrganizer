package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Registered;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredRepository extends CrudRepository<Registered, Integer> {
    Boolean existsByName(String name);
}
