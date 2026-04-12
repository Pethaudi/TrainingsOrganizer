import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal, untracked } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Store } from '@ngrx/store';
import UserState from './stores/user/user.state';
import { selectIsUserPending } from './stores/user/user.selectors';
import { SecurityService } from './services/security-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatIconButton, MatIcon],
  templateUrl: './app.html',
  styleUrl: './app.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App implements OnInit {
  protected readonly isDark = signal(false);
  readonly router = inject(Router);
  readonly securityService = inject(SecurityService);
  readonly store = inject(Store);
  readonly user = this.store.selectSignal<UserState>(state => state.user);
  readonly isUserPending = this.store.selectSignal(selectIsUserPending);
  private previousUser = untracked(this.user);

  readonly redirectToHomeIfUserIsSet = () => {
    const prev = this.previousUser.user;
    const current = this.user().user;
    const prevWasNullOrString = prev === null || typeof prev === 'string';
    const currentIsObject = current !== null && typeof current === 'object';
    this.previousUser = this.user();
    if (prevWasNullOrString && currentIsObject) {
      this.router.navigate(['/home']);
    }
  };
  redirectEffect = effect(this.redirectToHomeIfUserIsSet);

  ngOnInit() {
    const saved = localStorage.getItem('theme');
    if (saved === 'dark') {
      this.applyTheme(true);
    }

    this.securityService.authenticate();
  }

  toggleTheme() {
    this.applyTheme(!this.isDark());
  }

  applyTheme(dark: boolean) {
    this.isDark.set(dark);
    document.documentElement.classList.toggle('dark', dark);
    localStorage.setItem('theme', dark ? 'dark' : 'light');
  }
}
