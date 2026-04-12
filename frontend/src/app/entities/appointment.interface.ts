export default interface Appointment {
  id: number;
  course: {
    id: number,
    name: string
  };
  date: Date;
  note: string;
}
