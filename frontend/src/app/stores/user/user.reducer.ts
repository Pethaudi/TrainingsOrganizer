import { createReducer, on } from '@ngrx/store';
import { user } from './user.actions';
import UserState from './user.state';

export const initialState: UserState = {
  user: null,
  isUserPending: true
};

export const userReducer = createReducer(
  initialState,
  on(user.setUser, (_, { user }) => ({
    user,
    isUserPending: !user
  })),
  on(user.setIsUserPending, (state, { isUserPending }) => ({
    user: state.user,
    isUserPending
  }))
);
