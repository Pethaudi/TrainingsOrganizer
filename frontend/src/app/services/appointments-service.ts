import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import Appointment from '../entities/appointment.interface';
import AppointmentDto from '../entities/appointment-dto.interface';

@Injectable({
  providedIn: 'root',
})
export class AppointmentsService {
  private readonly baseUrl = 'http://localhost:8080/';

  private readonly http = inject(HttpClient);

  fetchAppointments(courseId: number) {
    return this.http.get<Array<Appointment>>(`${this.baseUrl}courses/${courseId}/appointments`);
  }

  createAppointment(appointment: AppointmentDto) {
    return this.http.post<Appointment>(
      `${this.baseUrl}appointments`,
      appointment
    );
  }

  deleteAppointment(id: number) {
    return this.http.delete<void>(`${this.baseUrl}appointments/${id}`);
  }
}
