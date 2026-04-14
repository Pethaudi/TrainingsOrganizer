package me.bitsandbites.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<CourseTrainer> trainers;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<CourseRegister> registrations;

    public Course() {}

    public Course(Integer id) {
        this.id = id;
    }

    public Course(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Course[id=%d, name=%s]",
                this.id,
                this.name
        );
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Registered> getTrainers() {
        return trainers.stream().map(CourseTrainer::getTrainer).toList();
    }

    public List<DogTeam> getRegistrations() {
        return registrations.stream().map(CourseRegister::getDogTeam).toList();
    }
}
