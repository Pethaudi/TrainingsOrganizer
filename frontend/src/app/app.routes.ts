import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Home } from './pages/home/home';
import { onlyLoggedInGuard } from './guards/only-logged-in-guard';
import { onlyLoggedOutGuard } from './guards/only-logged-out-guard';
import { Courses } from './pages/courses/courses';
import { CourseDetails } from './pages/courses/course-details/course-details';
import { Space } from './pages/space/space';

export const routes: Routes = [{
  path: 'login',
  component: Login,
  canActivate: [onlyLoggedOutGuard]
}, {
  path: '',
  component: Space,
  canActivate: [onlyLoggedInGuard],
  children: [{
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  }, {
    path: 'home',
    component: Home
  }, {
    path: 'courses',
    component: Courses,
    children: [{
      path: ':id',
      component: CourseDetails
    }]
  }]
}];
