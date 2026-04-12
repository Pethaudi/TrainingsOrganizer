import { createSelector } from "@ngrx/store";
import UserState from "./user.state";
import User from "../../entities/user.interface";
import { NgTemplateOutlet } from "@angular/common";

export const selectUser = (state: {
  user: UserState
}) => state.user;

export const selectUserError = createSelector(
  selectUser,
  (state: UserState) => {
    const user = state;
    if (typeof user === 'string') {
      return user;
    } else {
      return null;
    }
  }
);

export const selectUserLoggedIn = createSelector(
  selectUser,
  (state: UserState) => {
    return typeof state.user === 'object' && state.user !== null;
  }
);

export const selectIsUserPending = createSelector(
  selectUser,
  (state: UserState) => {
    return state.isUserPending;
  }
);

export const selectUserId = createSelector(
  selectUser,
  (state: UserState) => {
    if (state.user !== null && typeof state.user === 'object') {
      return (state.user as User).id;
    } else {
      return null;
    }
  }
);
