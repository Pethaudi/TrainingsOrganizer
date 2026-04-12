import { ChangeDetectionStrategy, Component, effect, inject, Signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { SecurityService } from '../../services/security-service';
import { Store } from '@ngrx/store';
import { selectUserError } from '../../stores/user/user.selectors';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login {
  private securityService = inject(SecurityService);
  private formBuilder = inject(FormBuilder);
  private store = inject(Store);
  protected userError = this.store.selectSignal(selectUserError);

  loginForm = this.formBuilder.group({
    username: ['', Validators.required]
  });

  async onLogin() {
    this.securityService.login(this.loginForm.value.username!);
    return false;
  }
}
