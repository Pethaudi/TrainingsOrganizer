package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Iterable<Appointment> findByCourseId(Integer id);
}
