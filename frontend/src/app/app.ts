import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectIsUserPending } from './stores/user/user.selectors';
import { ThemeService } from './services/theme.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App implements OnInit {
  private readonly themeService = inject(ThemeService);
  readonly store = inject(Store);
  readonly isUserPending = this.store.selectSignal(selectIsUserPending);

  ngOnInit() {
    this.themeService.init();
  }
}
