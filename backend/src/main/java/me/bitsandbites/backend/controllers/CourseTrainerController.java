package me.bitsandbites.backend.controllers;

import me.bitsandbites.backend.entities.CourseTrainer;
import me.bitsandbites.backend.repositories.CourseTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("course-trainer")
public class CourseTrainerController {
    private final CourseTrainerRepository courseTrainerRepository;

    @Autowired
    public CourseTrainerController(CourseTrainerRepository courseTrainerRepository) {
        this.courseTrainerRepository = courseTrainerRepository;
    }

    @GetMapping()
    public Iterable<CourseTrainer> index() {
        return this.courseTrainerRepository.findAll();
    }
}
