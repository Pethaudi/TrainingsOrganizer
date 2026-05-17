import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import CourseDetails from '../../entities/course-details.interface';
import { CoursesService } from '../../services/courses-service';
import { Store } from '@ngrx/store';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { selectUserOrganisations } from '../../stores/user/user.selectors';
import { OrganisationDto } from '../../entities/organisation-dto.interface';
import { Role } from '../../entities/member-of-organisation.interface';
import { forkJoin, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';

interface OrgWithCourses {
  organisation: OrganisationDto;
  role: Role;
  trainerCourses: CourseDetails[];
  memberCourses: CourseDetails[];
}

@Component({
  selector: 'app-home',
  imports: [RouterLink, MatButtonModule, MatIconModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Home {
  private readonly coursesService = inject(CoursesService);
  readonly dialog = inject(MatDialog);

  private readonly memberOfOrganisations = inject(Store).selectSignal(selectUserOrganisations);

  readonly orgsWithCourses = toSignal(
    toObservable(this.memberOfOrganisations).pipe(
      switchMap(memberships => {
        if (!memberships?.length) return of([]);

        const requests = memberships.map(m => {
          const member$ = this.coursesService.fetchCoursesAsMember(m.organisation.id);

          if (m.role === 'member') {
            return member$.pipe(
              map(memberCourses => ({ organisation: m.organisation, role: m.role, trainerCourses: [], memberCourses }))
            );
          }

          return forkJoin({
            trainerCourses: this.coursesService.fetchCoursesAsTrainer(m.organisation.id),
            memberCourses: member$,
          }).pipe(map(courses => ({ organisation: m.organisation, role: m.role, ...courses })));
        });

        return forkJoin(requests);
      })
    ),
    { initialValue: [] as OrgWithCourses[] }
  );
}
