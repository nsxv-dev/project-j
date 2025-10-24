import { Component } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule,
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  registerForm = new FormGroup({
    email:new FormControl<string>(""),
    password:new FormControl<string>(""),
    username:new FormControl<string>(""),
  })

  errorMessage:string = ""

  constructor(private authService:AuthService, private router:Router){
  }

  onSubmit(){
    const {username, email, password} = this.registerForm.value;
    if(!username || !email || !password)
      return;

    this.authService.register({username, email, password}).subscribe({
      next: () =>{
        this.router.navigate(["/posts"])
      },
      error: () => {
        this.errorMessage = 'Invalid email or password';
      }
    })
  }
}
