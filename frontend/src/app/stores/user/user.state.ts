import User from "../../entities/user.interface";

export default interface UserState {
  user: User | null | string,
  isUserPending: boolean
};
