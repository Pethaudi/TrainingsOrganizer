import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Home } from './pages/home/home';
import { onlyLoggedInGuard } from './guards/only-logged-in-guard';
import { onlyLoggedOutGuard } from './guards/only-logged-out-guard';

export const routes: Routes = [{
  path: 'login',
  component: Login,
  canActivate: [onlyLoggedOutGuard]
}, {
  path: 'home',
  component: Home,
  canActivate: [onlyLoggedInGuard]
}];
