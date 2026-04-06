package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.CourseTrainer;
import me.bitsandbites.backend.entities.CourseTrainerId;
import org.springframework.data.repository.CrudRepository;

public interface CourseTrainerRepository extends CrudRepository<CourseTrainer, CourseTrainerId> {
    Iterable<CourseTrainer> findAll();
}
