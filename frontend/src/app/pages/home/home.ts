import { ChangeDetectionStrategy, Component, effect, inject, OnInit, Signal, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import CourseDetails from '../../entities/course-details.interface';
import { CoursesService } from '../../services/courses-service';
import { Store } from '@ngrx/store';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import {
  MatDialog,
} from '@angular/material/dialog';
import { AddCourse } from '../../components/modals/add-course/add-course';
import { selectUserId } from '../../stores/user/user.selectors';
import { OrganisationDto } from '../../entities/organisation-dto.interface';
import { OrganisationsService } from '../../services/organisations-service';

@Component({
  selector: 'app-home',
  imports: [RouterLink, MatButtonModule, MatIconModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Home implements OnInit {
  private readonly coursesService = inject(CoursesService);
  private readonly organisationsService = inject(OrganisationsService);
  private readonly userId: Signal<number | null> = inject(Store).selectSignal(selectUserId);
  private readonly dialog = inject(MatDialog)
  
  readonly coursesAsTrainer = signal(new Array<CourseDetails>());
  readonly organisationsOfUser = signal(new Array<OrganisationDto>());

  constructor() {
    effect(() => console.log(this.coursesAsTrainer()));
  }

  ngOnInit() {
    this.coursesService.fetchCoursesToTeach()
      .subscribe({
        next: (courses) => this.coursesAsTrainer.set(courses)
    });

    this.organisationsService.fetchOrganisationsOfCurrentUser()
      .subscribe({
        next: (orgs) => this.organisationsOfUser.set(orgs)
      });
  }

  openAddCourse() {
    this.dialog.open(AddCourse).afterClosed().subscribe({
      next: (result: { name: string } | undefined) => {
        if (result) {
          this.coursesService.createCourse({
            name: result.name,
            trainers: [this.userId() ?? 0]
          }).subscribe({
            next: (newCourse) => {
              this.coursesAsTrainer.update(courses => [...courses, newCourse]);
            }
          })
        }
      }
    });
  }
}
