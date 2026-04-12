package me.bitsandbites.backend.dtos;

public class CourseMinimumDTO {
    private String name;
    private Iterable<Integer> trainers;

    public CourseMinimumDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<Integer> getTrainers() {
        return this.trainers;
    }

    public void setTrainers(Iterable<Integer> trainers) {
        this.trainers = trainers;
    }
}
