package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.CourseTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseTrainerRepository extends JpaRepository<CourseTrainer, Integer> {
    Iterable<CourseTrainer> findByCourseId(Integer id);

    @Query("""
            SELECT ct FROM coursetrainers ct
            JOIN FETCH ct.trainer
            JOIN FETCH ct.course
            WHERE ct.course.id IN (
                SELECT ct2.course.id FROM coursetrainers ct2 WHERE ct2.trainer.id = :trainerId
            )
            """)
    List<CourseTrainer> findAllByCoursesOfTrainer(@Param("trainerId") Integer trainerId);
}

