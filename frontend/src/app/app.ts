import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Store } from '@ngrx/store';
import { selectIsUserPending } from './stores/user/user.selectors';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatIconButton, MatIcon],
  templateUrl: './app.html',
  styleUrl: './app.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App implements OnInit {
  protected readonly isDark = signal(false);
  readonly store = inject(Store);
  readonly isUserPending = this.store.selectSignal(selectIsUserPending);

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
