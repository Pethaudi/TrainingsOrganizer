package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegisteredRepository extends JpaRepository<Registered, Integer> {
    Registered findByName(String name);

    @Query("SELECT CASE WHEN COUNT(ct) > 0 THEN true ELSE false END FROM coursetrainers ct WHERE ct.trainer.name = :name")
    boolean isTrainer(@Param("name") String name);

    @Query(
            value = "SELECT * FROM registered WHERE name = :name AND password = md5(:password)",
            nativeQuery = true
    )
    Optional<Registered> authenticateUser(@Param("name") String name, @Param("password") String password);
}
