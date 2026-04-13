import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-add-course',
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
  templateUrl: './add-course.html',
  styleUrl: './add-course.css',
})
export class AddCourse {
  private formBuilder = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<AddCourse>);

  addForm = this.formBuilder.group({
    name: ['', Validators.required],
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
