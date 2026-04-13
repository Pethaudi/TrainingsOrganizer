import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  readonly isDark = signal(false);

  init() {
    const saved = localStorage.getItem('theme');
    if (saved === 'dark') {
      this.applyTheme(true);
    }
  }

  toggle() {
    this.applyTheme(!this.isDark());
  }

  applyTheme(dark: boolean) {
    this.isDark.set(dark);
    document.documentElement.classList.toggle('dark', dark);
    localStorage.setItem('theme', dark ? 'dark' : 'light');
  }
}
