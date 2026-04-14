import { Course } from "./course.interface";
import { DogTeam } from "./dog-team.interface";

export interface CourseRegister {
  id: number;
  course: Course;
  dogTeam: DogTeam;
}
