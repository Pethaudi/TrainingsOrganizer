package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.CourseTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseTrainerRepository extends JpaRepository<CourseTrainer, Integer> {
    @Query(value = "SELECT ct.* FROM coursetrainers ct " +
                   "JOIN coursesoforganisation coo ON ct.courseid = coo.courseid " +
                   "WHERE ct.trainerid = :trainerId AND coo.organisationid = :organisationId",
           nativeQuery = true)
    List<CourseTrainer> findAllByTrainerIdAndOrganisationId(@Param("trainerId") Integer trainerId,
                                                            @Param("organisationId") Integer organisationId);
}
