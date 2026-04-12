import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal } from '@angular/core';
import User from '../entities/user.interface';
import { Store } from '@ngrx/store';
import { user } from '../stores/user/user.actions';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class SecurityService {
  private readonly baseUrl = 'http://localhost:8080/';
  private http = inject(HttpClient);
  private store = inject(Store);
  private router = inject(Router)

  login(creds: { username: string, password: string }) {
    this.http.post<User>(this.baseUrl + 'security/login', {
      username: creds.username,
      password: btoa(creds.password)
    }).subscribe({
      next: (value) => {
        this.store.dispatch(user.setUser({ user: value as any as User }))
      },
      error: () => {
        this.store.dispatch(user.setUser({ user: 'Something went wrong' }))
      }
    });
  }

  authenticate() {
    this.http.get<User>(this.baseUrl + 'security/authenticate')
      .subscribe({
        next: (value) => {
          this.store.dispatch(user.setUser({ user: value as any as User }))
        },
        error: () => {
          console.log('error');
          this.store.dispatch(user.setIsUserPending({ isUserPending: false }))
          this.router.navigateByUrl('/login');
        }
      });
  }
}
