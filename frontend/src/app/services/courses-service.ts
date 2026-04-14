import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import CourseDetails from '../entities/course-details.interface';
import { Course } from '../entities/course.interface';

@Injectable({
  providedIn: 'root',
})
export class CoursesService {
  private readonly baseUrl = 'http://localhost:8080/';

  private readonly http = inject(HttpClient);

  fetchCoursesToTeach() {
    return this.http.get<Array<CourseDetails>>(this.baseUrl + 'courses/as-trainer');
  }

  createCourse(course: {
    name: string,
    trainers: Array<number>
  }) {
    return this.http.post<CourseDetails>(this.baseUrl + 'courses', course)
  }

  fetchCourse(id: number) {
    return this.http.get<Course>(this.baseUrl + 'courses/' + id);
  }
}
