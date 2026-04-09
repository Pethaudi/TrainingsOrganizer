import { ChangeDetectionStrategy, Component, effect, inject, OnInit, signal, untracked } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Store } from '@ngrx/store';

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
  readonly store = inject(Store);
  readonly user = this.store.selectSignal(state => state.user);
  private previousUser = untracked(this.user);
  userEffect = effect(() => {
    const prev = this.previousUser;
    const current = this.user();
    const prevWasNullOrString = prev === null || typeof prev === 'string';
    const currentIsObject = current !== null && typeof current === 'object';
    this.previousUser = current;
    if (prevWasNullOrString && currentIsObject) {
      this.router.navigate(['/home']);
    }
  });

  ngOnInit() {
    const saved = localStorage.getItem('theme');
    if (saved === 'dark') {
      this.applyTheme(true);
    }
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
