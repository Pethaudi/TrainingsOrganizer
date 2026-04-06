package me.bitsandbites.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "coursetrainers")
public class CourseTrainer {

    @EmbeddedId
    private CourseTrainerId id;

    @ManyToOne
    @MapsId("trainerId")
    @JoinColumn(name = "trainerid", referencedColumnName = "id")
    private Registered trainer;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "courseid", referencedColumnName = "id")
    private Course course;

    public CourseTrainerId getId() { return id; }
    public Registered getTrainer() { return trainer; }
    public Course getCourse() { return course; }
}
