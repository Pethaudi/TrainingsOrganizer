import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';

@Component({
  selector: 'app-add-appointment',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
  ],
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
