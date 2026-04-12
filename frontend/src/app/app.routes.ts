import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Home } from './pages/home/home';
import { onlyLoggedInGuard } from './guards/only-logged-in-guard';
import { onlyLoggedOutGuard } from './guards/only-logged-out-guard';
import { Courses } from './pages/courses/courses';
import { CourseDetails } from './pages/courses/course-details/course-details';

export const routes: Routes = [{
  path: 'courses',
  component: Courses,
  canActivate: [onlyLoggedInGuard],
  children: [{
    path: ':id',
    component: CourseDetails
  }]
}, {
  path: 'login',
  component: Login,
  canActivate: [onlyLoggedOutGuard]
}, {
  path: 'home',
  component: Home,
  canActivate: [onlyLoggedInGuard]
}];
