package me.bitsandbites.backend.dtos;

import me.bitsandbites.backend.entities.CourseTrainer;

public class CourseDTO {
    private Integer id;
    private String name;
    private Iterable<UserDTO> trainers;

    public CourseDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CourseDTO(CourseTrainer course) {
        this.id = course.getCourse().getId();
        this.name = course.getCourse().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<UserDTO> getTrainers() {
        return this.trainers;
    }

    public void setTrainers(Iterable<UserDTO> trainers) {
        this.trainers = trainers;
    }
}
