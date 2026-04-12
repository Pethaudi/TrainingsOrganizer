import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal } from '@angular/core';
import User from '../entities/user.interface';
import { Store } from '@ngrx/store';
import { user } from '../stores/user/user.actions';

@Injectable({
  providedIn: 'root',
})
export class SecurityService {
  private readonly baseUrl = 'http://localhost:8080/';
  private http = inject(HttpClient);
  private store = inject(Store);

  login(username: string) {
    this.http.get<User>(this.baseUrl + 'security/login', {
      params: {
        username
      }
    }).subscribe({
      next: (value) => {
        this.store.dispatch(user.setUser({ user: value as any as User }))
        localStorage.setItem('user', JSON.stringify(value));
      },
      error: () => {
        this.store.dispatch(user.setUser({ user: 'Something went wrong' }))
      }
    });
  }
}
