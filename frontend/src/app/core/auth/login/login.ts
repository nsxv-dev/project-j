import { Component } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCardModule } from "@angular/material/card";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatButtonModule
],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  loginForm = new FormGroup({
    email: new FormControl<string>(""),
    password: new FormControl<string>(""),
  })
  errorMessage:string = "";

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    const {email, password} = this.loginForm.value;
    if(!email || !password)
      return;

    this.authService.login({email, password}).subscribe({
      next: () => {
        this.router.navigate(['/posts']);
      },
      error: () => {
        this.errorMessage = 'Invalid email or password';
      }
    });
  }
}
