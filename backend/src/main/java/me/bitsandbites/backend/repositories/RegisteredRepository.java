package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Registered;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredRepository extends CrudRepository<Registered, Integer> {
    Registered findByName(String name);

    @Query(
            value = "SELECT EXISTS (SELECT 1 FROM coursetrainers trainers JOIN registered registers ON trainers.trainerid = registers.id WHERE registers.name = ?1)",
            nativeQuery = true
    )
    Boolean isTrainer(String name);
}
