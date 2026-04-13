package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
