package me.bitsandbites.backend.controllers;

import me.bitsandbites.backend.dtos.CourseDetailsDTO;
import me.bitsandbites.backend.dtos.CourseMinimumDTO;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.dtos.UserDTO;
import me.bitsandbites.backend.entities.Course;
import me.bitsandbites.backend.entities.CourseTrainer;
import me.bitsandbites.backend.repositories.CourseRepository;
import me.bitsandbites.backend.repositories.CourseTrainerRepository;
import me.bitsandbites.backend.repositories.RegisteredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("course")
public class CourseController {
    private final CourseTrainerRepository courseTrainerRepository;
    private final CourseRepository courseRepository;
    private final RegisteredRepository registeredRepository;

    @Autowired
    public CourseController(
            CourseTrainerRepository courseTrainerRepository,
            CourseRepository courseRepository,
            RegisteredRepository registeredRepository
    ) {
        this.courseTrainerRepository = courseTrainerRepository;
        this.courseRepository = courseRepository;
        this.registeredRepository = registeredRepository;
    }

    @PostMapping()
    @Transactional
    public CourseDetailsDTO createCourseDetails(@RequestBody CourseMinimumDTO courseMinimumDTO) {
        var course = courseRepository.save(new Course(courseMinimumDTO.getName()));
        var trainers = StreamSupport.stream(courseMinimumDTO.getTrainers().spliterator(), false)
                .map(registeredRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        this.courseTrainerRepository.saveAll(
                trainers.stream().map(trainer -> new CourseTrainer(course, trainer)).toList()
        );

        var result = new CourseDetailsDTO(course.getId(), course.getName());
        result.setTrainers(
            trainers.stream().map(trainer -> new UserDTO(trainer.getId(), trainer.getName(), Role.Trainer)).toList()
        );
        return result;
    }
}
