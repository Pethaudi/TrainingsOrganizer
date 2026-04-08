package me.bitsandbites.backend.controllers;

import me.bitsandbites.backend.entities.CourseTrainer;
import me.bitsandbites.backend.repositories.CourseTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("trainer")
public class TrainerController {
    private final CourseTrainerRepository courseTrainerRepository;

    @Autowired
    public TrainerController(CourseTrainerRepository courseTrainerRepository) {
        this.courseTrainerRepository = courseTrainerRepository;
    }

    @GetMapping( value = "/courses/{trainerId}")
    public Iterable<CourseTrainer> index(@PathVariable("trainerId") Integer trainerId) {
        return courseTrainerRepository.findByTrainerId(trainerId);
    }
}
