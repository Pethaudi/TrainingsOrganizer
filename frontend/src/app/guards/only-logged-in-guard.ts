import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectUserLoggedIn } from '../stores/user/user.selectors';

export const onlyLoggedInGuard: CanActivateFn = () => {
  const isLoggedIn = inject(Store).selectSignal(selectUserLoggedIn)();
  return isLoggedIn || inject(Router).createUrlTree(['/login']);
};
