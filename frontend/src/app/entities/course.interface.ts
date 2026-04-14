import Appointment from "./appointment.interface";
import { CourseRegister } from "./course-register.interface";
import { DogTeam } from "./dog-team.interface";
import User from "./user.interface";

export interface Course {
  id: number;
  name: string;
  appointments?: Appointment[];
  trainers?: User[];
  registrations?: DogTeam[];
}
