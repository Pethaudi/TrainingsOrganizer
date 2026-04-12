import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import Appointment from '../entities/appointment.interface';

@Injectable({
  providedIn: 'root',
})
export class AppointmentsService {
  private readonly baseUrl = 'http://localhost:8080/';

  private readonly http = inject(HttpClient);

  fetchAppointments(courseId: number) {
    return this.http.get<Array<Appointment>>(`${this.baseUrl}courses/${courseId}/appointments`);
  }
}
