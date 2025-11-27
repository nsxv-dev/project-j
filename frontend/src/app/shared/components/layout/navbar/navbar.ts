import { CommonModule } from '@angular/common';
import { Component, computed } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../../core/services/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, MatButtonModule, RouterLink, CommonModule, MatIconModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  constructor(public authService: AuthService) {}

  // Links for logged-in users
  private loggedInLinks = [
    { label: 'Home', icon: 'home', routerLink: '/' },
    { label: 'My Posts', icon: 'article', routerLink: '/posts/my' },
    { label: 'My Profile', icon: 'account_circle', routerLink: '/user/me' },
  ];

  // Links for guests
  private guestLinks = [
    { label: 'Home', icon: 'home', routerLink: '/' },
    { label: 'Log in', icon: 'person', routerLink: '/login' },
    { label: 'Register', icon: 'person_add', routerLink: '/register' },
  ];

  // Automatically pick correct links based on auth state
  links = computed(() => (this.authService.isLoggedIn() ? this.loggedInLinks : this.guestLinks));
}
