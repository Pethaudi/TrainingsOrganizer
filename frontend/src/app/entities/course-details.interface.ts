import Appointment from './appointment.interface';
import { DogTeam } from './dog-team.interface';
import { Registered } from './registered.interface';

export default interface CourseDetails {
  id: number;
  name: string;
  appointments: Appointment[];
  trainers: Registered[];
  registrations: DogTeam[];
}
