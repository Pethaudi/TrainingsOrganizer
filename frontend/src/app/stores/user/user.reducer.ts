import { createReducer, on } from '@ngrx/store';
import { user } from './user.actions';
import User from '../../entities/user.interface';

export const initialState: User | null | string = null;

export const userReducer = createReducer(
  initialState,
  on(user.setUser, (_, { user }) => user as null)
);
