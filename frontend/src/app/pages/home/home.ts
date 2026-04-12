import { Component, inject, OnInit, Signal, signal } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import CourseDetails from '../../entities/course-details.interface';
import { CoursesServices } from '../../services/courses-services';
import { Store } from '@ngrx/store';
import User from '../../entities/user.interface';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import {
  MAT_DIALOG_DATA,
  MatDialog,
} from '@angular/material/dialog';
import { AddCourse } from '../../components/modals/add-course/add-course';

@Component({
  selector: 'app-home',
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
  private readonly coursesService = inject(CoursesServices);
  private readonly userStore: Signal<User> = inject(Store).selectSignal(store => store.user);
  private readonly dialog = inject(MatDialog)
  
  readonly coursesAsTrainer = signal(new Array<CourseDetails>());

  ngOnInit() {
    this.coursesService.coursesOfTrainer(this.userStore().id)
      .subscribe({
        next: this.coursesAsTrainer.set
      });
  }

  openAddCourse() {
    this.dialog.open(AddCourse).afterClosed().subscribe({
      next: (result: { name: string } | undefined) => {
        if (result) {
          console.log('New course:', result);
          this.coursesService.createCourse({
            name: result.name,
            trainers: [this.userStore().id]
          }).subscribe({
            next: (newCourse) => {
              this.coursesAsTrainer().push(newCourse);
            }
          })
        }
      }
    });
  }
}
