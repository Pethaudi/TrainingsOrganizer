import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { switchMap } from 'rxjs';
import Appointment from '../../../entities/appointment.interface';
import { AppointmentsService } from '../../../services/appointments-service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { AddAppointment } from '../../../components/modals/add-appointment/add-appointment';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-course-details',
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './course-details.html',
  styleUrl: './course-details.css',
})
export class CourseDetails implements OnInit {
  appointmentsService = inject(AppointmentsService);
  activatedRoute = inject(ActivatedRoute);
  dialog = inject(MatDialog);
  private readonly destroyRef = inject(DestroyRef);
  appointments = signal(new Array<Appointment>);
  currentCourseId = signal(0);

  ngOnInit(): void {
    this.activatedRoute.params.pipe(
      switchMap((params) => {
        this.currentCourseId.set(+params['id']);
        return this.appointmentsService.fetchAppointments(this.currentCourseId());
      }),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: (appointments) => this.appointments.set(appointments)
    });
  }

  deleteAppointment(id: number) {
    this.appointmentsService.deleteAppointment(id).subscribe({
      next: () => this.appointments.set(this.appointments().filter(a => a.id !== id))
    });
  }

  openAddAppointment() {
    this.dialog.open(AddAppointment).afterClosed().subscribe({
      next: (result: { note: string, date: string } | undefined) => {
        if (result) {
          this.appointmentsService.createAppointment({
            relationId: this.currentCourseId(),
            note: result.note,
            date: new Date(result.date).toISOString()
          }).subscribe({
            next: (appointment) => this.appointments.set([...this.appointments(), appointment])
          });
        }
      }
    });
  }
}
