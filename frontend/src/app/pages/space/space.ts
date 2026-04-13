import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-space',
  imports: [RouterLink, RouterLinkActive, RouterOutlet, MatIconModule],
  templateUrl: './space.html',
  styleUrl: './space.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Space {
  protected readonly theme = inject(ThemeService);
  protected readonly isFullscreen = signal(false);

  toggleFullscreen() {
    this.isFullscreen.update(v => !v);
  }

  closeSidebar() {
    this.isFullscreen.set(false);
  }
}
