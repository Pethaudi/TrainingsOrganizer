package me.bitsandbites.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.bitsandbites.backend.annotations.RequiresAuth;
import me.bitsandbites.backend.dtos.CourseDTO;
import me.bitsandbites.backend.dtos.CourseMinimumDTO;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.dtos.UserDTO;
import me.bitsandbites.backend.entities.Appointment;
import me.bitsandbites.backend.entities.Course;
import me.bitsandbites.backend.entities.CourseTrainer;
import me.bitsandbites.backend.helpers.TokenParser;
import me.bitsandbites.backend.repositories.AppointmentRepository;
import me.bitsandbites.backend.repositories.CourseRepository;
import me.bitsandbites.backend.repositories.CourseTrainerRepository;
import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("courses")
public class CoursesController {
    private final CourseTrainerRepository courseTrainerRepository;
    private final CourseRepository courseRepository;
    private final RegisteredRepository registeredRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public CoursesController(
            CourseTrainerRepository courseTrainerRepository,
            CourseRepository courseRepository,
            RegisteredRepository registeredRepository,
            AppointmentRepository appointmentRepository
    ) {
        this.courseTrainerRepository = courseTrainerRepository;
        this.courseRepository = courseRepository;
        this.registeredRepository = registeredRepository;
        this.appointmentRepository = appointmentRepository;
    }


    @PostMapping()
    @Transactional
    @RequiresAuth()
    public void createCourseDetails(@RequestBody CourseMinimumDTO courseMinimumDTO) {
    }

    @GetMapping("/as-trainer")
    @RequiresAuth(role = Role.trainer)
    public void getCoursesOfAsTrainer(HttpServletRequest request) {
    }


    @GetMapping("{courseId}")
    @RequiresAuth(role = Role.trainer)
    public Course getCourseById(@PathVariable Integer courseId){
        return this.courseRepository.findById(courseId).orElse(null);
    }

    @GetMapping("/{courseId}/appointments")
    @RequiresAuth()
    public Iterable<Appointment> getCoursesByUser(@PathVariable Integer courseId) {
        return this.appointmentRepository.findByCourseId(courseId);
    }
}
