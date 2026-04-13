package me.bitsandbites.backend.controllers;

import me.bitsandbites.backend.annotations.RequiresAuth;
import me.bitsandbites.backend.dtos.AppointmentDTO;
import me.bitsandbites.backend.entities.Appointment;
import me.bitsandbites.backend.repositories.AppointmentRepository;
import me.bitsandbites.backend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("appointments")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public AppointmentController(
            AppointmentRepository appointmentRepository,
            CourseRepository courseRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping()
    @RequiresAuth()
    public Appointment createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setCourse(courseRepository.getReferenceById(appointmentDTO.getRelationId()));
        appointment.setDate(appointmentDTO.getDate());
        appointment.setNote(appointmentDTO.getNote());
        return appointmentRepository.save(appointment);
    }

    @DeleteMapping("{id}")
    @RequiresAuth()
    public void deleteAppointment(@PathVariable Integer id) {
        appointmentRepository.deleteById(id);
    }
}
