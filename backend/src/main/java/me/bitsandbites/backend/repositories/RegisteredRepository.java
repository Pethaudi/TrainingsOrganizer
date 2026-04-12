package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Registered;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegisteredRepository extends CrudRepository<Registered, Integer> {
    Registered findByName(String name);
    Optional<Registered> findById(Integer id);

    @Query(
            value = "SELECT EXISTS (SELECT 1 FROM coursetrainers trainers JOIN registered registers ON trainers.trainerid = registers.id WHERE registers.name = ?1)",
            nativeQuery = true
    )
    Boolean isTrainer(String name);

    @Query(
            value = "SELECT id, name FROM registered WHERE name = ?1 AND password = crypt(?2, 'md5')",
            nativeQuery = true
    )
    Optional<Registered> authenticateUser(String name, String password);

    @Query(
            value = "SELECT EXISTS (SELECT id, name FROM registered WHERE name = ?1 AND password = crypt(?2, 'md5'))",
            nativeQuery = true
    )
    boolean isUserAndPasswordCorrect(String username, String password);
}
