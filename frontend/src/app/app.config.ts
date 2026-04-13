import { ApplicationConfig, inject, provideBrowserGlobalErrorListeners, provideAppInitializer } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HttpInterceptorFn, provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideStore } from '@ngrx/store';

import { userReducer } from './stores/user/user.reducer';
import { SecurityService } from './services/security-service';

const withCredentialsInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req.clone({ withCredentials: true }));
};

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptors([withCredentialsInterceptor])),
    provideStore({ user: userReducer }),
    provideAppInitializer(() => inject(SecurityService).authenticate()),
  ],
};
