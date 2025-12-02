import { Component, OnInit } from '@angular/core';
import { UserProfileUpdate } from '../../models/user-profile-update';
import {
  Validators,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
} from '@angular/forms';
import { UserProfile } from '../../models/user-profile';
import { UserService } from '../user-service';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldControl } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { SkillService } from '../skill-service';
import { forkJoin } from 'rxjs/internal/observable/forkJoin';
import { Skill } from '../../models/skill';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile-edit',
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatOptionModule,
    CommonModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatButtonModule,
  ],
  templateUrl: './profile-edit.html',
  styleUrl: './profile-edit.scss',
})
export class ProfileEdit implements OnInit {
  form: FormGroup;
  profile!: UserProfile;
  availableSkills: Skill[] = [];
  isLoading = true;
  roles = ['BEGINNER', 'ADVANCED', 'PROFESSIONAL', 'MENTOR'];

  constructor(
    private userService: UserService,
    private skillService: SkillService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar // ✅ inject snack bar
  ) {
    this.form = new FormGroup({
      displayName: new FormControl(''),
      role: new FormControl(''),
      avatarUrl: new FormControl(''),
      githubUrl: new FormControl(''),
      linkedinUrl: new FormControl(''),
      skillIds: new FormControl([]), // multiple select
    });
  }

  ngOnInit(): void {
    // Load both profile and skills at the same time
    forkJoin({
      profile: this.userService.getCurrentUserProfile(),
      skills: this.skillService.getAllSkills(),
    }).subscribe({
      next: ({ profile, skills }) => {
        this.profile = profile;
        this.availableSkills = skills;

        // Patch form values after both are loaded
        this.form.patchValue({
          displayName: profile.displayName,
          role: profile.role,
          avatarUrl: profile.avatarUrl,
          githubUrl: profile.githubUrl,
          linkedinUrl: profile.linkedinUrl,
          skillIds: profile.skills.map((s) => s.id),
        });

        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to load profile or skills', err);
        this.isLoading = false;
      },
    });
  }

  save(): void {
    if (this.form.invalid) {
      return;
    }

    const formValue = this.form.value;

    const updateDto: UserProfileUpdate = {
      displayName: formValue.displayName,
      avatarUrl: formValue.avatarUrl,
      role: formValue.role,
      githubUrl: formValue.githubUrl,
      linkedinUrl: formValue.linkedinUrl,
      skillIds: formValue.skillIds,
    };

    this.userService.updateProfile(updateDto).subscribe({
      next: () => {
        this.snackBar.open('Profile updated successfully!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
        });
      },
      error: (err) => {
        console.error('Error updating profile', err);
        this.snackBar.open('Failed to update profile.', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
        });
      },
    });
  }
}
