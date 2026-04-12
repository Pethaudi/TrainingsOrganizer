package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.CourseTrainer;
import org.hibernate.service.Service;
import org.springframework.data.repository.CrudRepository;

public interface CourseTrainerRepository extends CrudRepository<CourseTrainer, Integer> {
    Iterable<CourseTrainer> findByTrainerId(Integer id);
    Iterable<CourseTrainer> findByCourseId(Integer id);
}

