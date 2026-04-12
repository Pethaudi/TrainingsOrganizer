import User from "./user.interface";

export default interface CourseDetails {
  id: number;
  name: string;
  trainers: Array<User>
}