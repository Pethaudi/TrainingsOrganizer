package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.CourseRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Integer> {
    @Query(value = "SELECT cr.* FROM courseregisters cr " +
                   "JOIN dogteams dt ON cr.dogteamid = dt.id " +
                   "JOIN coursesoforganisation coo ON cr.courseid = coo.courseid " +
                   "WHERE dt.handlerid = :registeredId AND coo.organisationid = :organisationId",
           nativeQuery = true)
    List<CourseRegister> findAllByHandlerIdAndOrganisationId(@Param("registeredId") Integer registeredId,
                                                             @Param("organisationId") Integer organisationId);
}
