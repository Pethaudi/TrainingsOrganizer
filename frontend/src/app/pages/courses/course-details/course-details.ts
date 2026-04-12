import { Component, inject, OnInit, signal } from '@angular/core';
import Appointment from '../../../entities/appointment.interface';
import { AppointmentsService } from '../../../services/appointments-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-course-details',
  imports: [],
  templateUrl: './course-details.html',
  styleUrl: './course-details.css',
})
export class CourseDetails implements OnInit {
  appointmentsService = inject(AppointmentsService);
  activatedRoute = inject(ActivatedRoute);
  appointments = signal(new Array<Appointment>);

  ngOnInit(): void {
    this.activatedRoute.params.subscribe({
      next: (params) => {
        this.appointmentsService.fetchAppointments(+params['id']).subscribe({
          next: this.appointments.set
        });
      }
    });
  }
}
