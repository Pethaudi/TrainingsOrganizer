import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { switchMap } from 'rxjs';
import Appointment from '../../../entities/appointment.interface';
import { AppointmentsService } from '../../../services/appointments-service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AddAppointment } from '../../../components/modals/add-appointment/add-appointment';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-course-details',
  imports: [MatButtonModule, MatIconModule, DatePipe],
  templateUrl: './course-details.html',
  styleUrl: './course-details.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CourseDetails implements OnInit {
  private readonly appointmentsService = inject(AppointmentsService);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly dialog = inject(MatDialog);
  private readonly location = inject(Location);
  private readonly destroyRef = inject(DestroyRef);

  readonly appointments = signal(new Array<Appointment>());
  readonly currentCourseId = signal(0);
  readonly courseName = computed(() => this.appointments()[0]?.course?.name ?? 'Course');

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

  goBack() {
    this.location.back();
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
