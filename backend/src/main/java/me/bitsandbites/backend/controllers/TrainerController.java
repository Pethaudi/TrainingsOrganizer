package me.bitsandbites.backend.controllers;

import me.bitsandbites.backend.dtos.CourseDetailsDTO;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.dtos.UserDTO;
import me.bitsandbites.backend.entities.CourseTrainer;
import me.bitsandbites.backend.repositories.CourseTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("trainer")
public class TrainerController {
    private final CourseTrainerRepository courseTrainerRepository;

    @Autowired
    public TrainerController(CourseTrainerRepository courseTrainerRepository) {
        this.courseTrainerRepository = courseTrainerRepository;
    }

    @GetMapping( value = "/courses/{trainerId}")
    public Iterable<CourseDetailsDTO> index(@PathVariable("trainerId") Integer trainerId) {
        return StreamSupport.stream(courseTrainerRepository.findByTrainerId(trainerId).spliterator(), true)
                .map(CourseDetailsDTO::new)
                .peek(course -> course.setTrainers(
                        StreamSupport.stream(courseTrainerRepository.findByCourseId(course.getId()).spliterator(), false)
                                .map(CourseTrainer::getTrainer)
                                .map(trainer -> new UserDTO(trainer.getId(), trainer.getName(), Role.Trainer))
                                .toList()
                ))
                .toList();
    }
}
