import { createActionGroup, props } from '@ngrx/store';
import User from '../../entities/user.interface';

export const user = createActionGroup({
  source: 'user',
  events: {
    'setUser': props<{ user: User | null | string }>()
  }
});
