import { Signal } from "@angular/core";
import { createSelector } from "@ngrx/store";
import User from "../../entities/user.interface";

export const selectUser = (state: { user: User | null | string}) => state.user;
export const selectUserError = createSelector(
  selectUser,
  (state: User | null | string) => {
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
  (state: User | null | string) => {
    return typeof state === 'object' && state !== null;
  }
)