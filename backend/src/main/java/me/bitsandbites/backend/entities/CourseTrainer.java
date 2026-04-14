package me.bitsandbites.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "coursetrainers")
@Table(name = "coursetrainers")
public class CourseTrainer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainerid", referencedColumnName = "id")
    private Registered trainer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseid", referencedColumnName = "id")
    private Course course;

    public CourseTrainer() {}

    public CourseTrainer(Integer courseId, Integer trainerId) {
        this.course = new Course(courseId);
        this.trainer = new Registered(trainerId);
    }

    public CourseTrainer(Course course, Registered trainer) {
        this.course = course;
        this.trainer = trainer;
    }

    public Integer getId() { return id; }
    public Registered getTrainer() { return trainer; }
    public Course getCourse() { return course; }
}
