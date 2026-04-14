package me.bitsandbites.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
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
public class CourseController {
    private final CourseTrainerRepository courseTrainerRepository;
    private final CourseRepository courseRepository;
    private final RegisteredRepository registeredRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public CourseController(
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
    public CourseDTO createCourseDetails(@RequestBody CourseMinimumDTO courseMinimumDTO) {
        var course = courseRepository.save(new Course(courseMinimumDTO.getName()));
        var trainers = StreamSupport.stream(courseMinimumDTO.getTrainers().spliterator(), false)
                .map(registeredRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        this.courseTrainerRepository.saveAll(
                trainers.stream().map(trainer -> new CourseTrainer(course, trainer)).toList()
        );

        var result = new CourseDTO(course.getId(), course.getName());
        result.setTrainers(
            trainers.stream().map(trainer -> new UserDTO(trainer.getId(), trainer.getName(), Role.Trainer)).toList()
        );
        return result;
    }

    @GetMapping("/as-trainer")
    @RequiresAuth(role = Role.Trainer)
    public List<CourseDTO> getCoursesOfAsTrainer(HttpServletRequest request) {
        var user = TokenParser.parseFromRequest(request);
        return courseTrainerRepository.findAllByCoursesOfTrainer(user.getId()).stream()
                .collect(Collectors.groupingBy(ct -> ct.getCourse().getId()))
                .values().stream()
                .map(relations -> {
                    var dto = new CourseDTO(relations.get(0));
                    dto.setTrainers(
                            relations.stream()
                                    .map(ct -> new UserDTO(ct.getTrainer().getId(), ct.getTrainer().getName(), Role.Trainer))
                                    .toList()
                    );
                    return dto;
                })
                .toList();
    }


    @GetMapping("{courseId}")
    @RequiresAuth(role = Role.Trainer)
    public Course getCourseById(@PathVariable Integer courseId){
        return this.courseRepository.findById(courseId).orElse(null);
    }

    @GetMapping("/{courseId}/appointments")
    @RequiresAuth()
    public Iterable<Appointment> getCoursesByUser(@PathVariable Integer courseId) {
        return this.appointmentRepository.findByCourseId(courseId);
    }
}
