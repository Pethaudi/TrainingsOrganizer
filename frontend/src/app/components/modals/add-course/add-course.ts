import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';

@Component({
  selector: 'app-add-course',
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
  templateUrl: './add-course.html',
  styleUrl: './add-course.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
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
