import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import CourseDetails from '../entities/course-details.interface';

@Injectable({
  providedIn: 'root',
})
export class CoursesServices {
  private readonly baseUrl = 'http://localhost:8080/';

  private readonly http = inject(HttpClient);

  coursesOfTrainer(trainerId: number) {
    return this.http.get<Array<CourseDetails>>(this.baseUrl + 'trainer/courses/' + trainerId);
  }

  createCourse(course: {
    name: string,
    trainers: Array<number>
  }) {
    return this.http.post<CourseDetails>(this.baseUrl + 'course', course)
  }
}
