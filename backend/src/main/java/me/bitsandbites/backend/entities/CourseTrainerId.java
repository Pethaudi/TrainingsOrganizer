package me.bitsandbites.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourseTrainerId implements Serializable {
    @Column(name = "courseid")
    private Integer courseId;
    @Column(name = "trainerid")
    private Integer trainerId;

    public CourseTrainerId() {}

    public CourseTrainerId(Integer courseId, Integer trainerId) {
        this.courseId = courseId;
        this.trainerId = trainerId;
    }

    public Integer getCourseId() { return courseId; }
    public Integer getTrainerId() { return trainerId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            if (!(o instanceof CourseTrainerId that)) {
                return false;
            } else {
                return Objects.equals(courseId, that.courseId) && Objects.equals(trainerId, that.trainerId);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, trainerId);
    }
}