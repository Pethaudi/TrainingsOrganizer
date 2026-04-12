import { createReducer, on } from '@ngrx/store';
import { user } from './user.actions';
import User from '../../entities/user.interface';

function createInitialState() {
  if (localStorage) {
    try {
      return JSON.parse(localStorage.getItem('user')!);
    } catch {
      return null;
    }
  } else {
    return null;
  }
}

export const initialState: User | null | string = createInitialState();

export const userReducer = createReducer(
  initialState,
  on(user.setUser, (_, { user }) => user)
);
