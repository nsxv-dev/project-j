import { Component, OnInit } from '@angular/core';
import { UserProfile } from '../../models/user-profile';
import { UserService } from '../user-service';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../core/services/auth-service';

@Component({
  selector: 'app-profile',
  imports: [MatCardModule, CommonModule, MatListModule, MatIconModule, MatButtonModule, RouterLink],
  templateUrl: './profile.html',
  styleUrl: './profile.scss',
})
export class Profile implements OnInit {
  userId: string | null = null;
  profile!: UserProfile;
  currentUserId: number | null = null;
  constructor(
    private userService: UserService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();

    this.route.paramMap.subscribe((params) => {
      this.userId = params.get('id');

      this.userService.getProfile(this.userId).subscribe((profile) => {
        this.profile = profile;
      });
    });
  }

  isMyProfile(): boolean {
    return this.profile?.email === this.authService.getCurrentUserEmail();
  }
}
