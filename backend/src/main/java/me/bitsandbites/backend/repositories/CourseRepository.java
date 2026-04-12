package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    Optional<Course> findById(Integer id);
}
