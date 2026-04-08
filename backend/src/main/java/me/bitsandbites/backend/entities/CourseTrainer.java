package me.bitsandbites.backend.entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity(name = "coursetrainers")
@Table(name = "coursetrainers")
public class CourseTrainer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @MapsId("trainerId")
    @JoinColumn(name = "trainerid", referencedColumnName = "id")
    private Registered trainer;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "courseid", referencedColumnName = "id")
    private Course course;

    public Integer getId() { return id; }
    public Registered getTrainer() { return trainer; }
    public Course getCourse() { return course; }
}
