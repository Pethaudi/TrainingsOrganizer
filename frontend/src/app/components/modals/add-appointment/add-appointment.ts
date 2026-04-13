import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-add-appointment',
  imports: [
    ReactiveFormsModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './add-appointment.html',
  styleUrl: './add-appointment.css',
})
export class AddAppointment {
  private formBuilder = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<AddAppointment>);

  addForm = this.formBuilder.group({
    note: ['', Validators.required],
    date: [new Date().toISOString().slice(0, 16), Validators.required]
  });

  abort() {
    this.dialogRef.close();
  }

  save() {
    if (this.addForm.valid) {
      this.dialogRef.close(this.addForm.value);
    }
  }
}
