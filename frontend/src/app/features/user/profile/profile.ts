import { Component, OnInit } from '@angular/core';
import { UserProfile } from '../../models/user-profile';
import { UserService } from '../user-service';
import { MatCardModule } from "@angular/material/card";
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatListModule } from "@angular/material/list";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-profile',
  imports: [MatCardModule, CommonModule, MatListModule, MatIconModule, MatButtonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.scss'
})
export class Profile implements OnInit{

  userId: string | null = null;
  profile!: UserProfile;

  constructor(private userService: UserService, private route: ActivatedRoute) {}

ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
    this.userId = params.get('id');
    this.userService.getProfile(this.userId).subscribe(p => {
      this.profile = p
      })
  })
};
}