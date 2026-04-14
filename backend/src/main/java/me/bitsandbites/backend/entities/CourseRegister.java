package me.bitsandbites.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "courseregisters")
public class CourseRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseid", referencedColumnName = "id")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dogteamid", referencedColumnName = "id")
    private DogTeam dogTeam;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public DogTeam getDogTeam() { return dogTeam; }
    public void setDogTeam(DogTeam dogTeam) { this.dogTeam = dogTeam; }
}
