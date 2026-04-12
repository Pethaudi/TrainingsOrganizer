package me.bitsandbites.backend.repositories;

import me.bitsandbites.backend.entities.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    Iterable<Appointment> findByCourseId(Integer id);
}
