import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

interface LoginRequest {
  email: string;
  password: string;
}

interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

interface AuthResponse {
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}`;
  public _isLoggedIn = signal<boolean>(false);
  private _token = signal<string | null>(null);

  isLoggedIn = this._isLoggedIn.asReadonly();
  private http: HttpClient = inject(HttpClient);

  constructor(private router: Router) {
    const token = localStorage.getItem('token');
    this._isLoggedIn.set(!!token);
    this._token.set(token);
  }

  login(data: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/api/auth/login`, data).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
        this._token.set(response.token);
        this._isLoggedIn.set(true);
      })
    );
  }

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/api/auth/register`, data).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
        this._token.set(response.token);
        this._isLoggedIn.set(true);
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    this._token.set(null);
    this._isLoggedIn.set(false);
    this.router.navigate(['/']);
  }

  getToken(): string | null {
    return this._token();
  }

  getCurrentUserId(): number | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded = jwtDecode<DecodedToken>(token);
      return Number(decoded.jti); // <-- this is your user ID from the token
    } catch {
      return null;
    }
  }

  getCurrentUserEmail(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded = jwtDecode<DecodedToken>(token);
      return decoded.sub; // email
    } catch {
      return null;
    }
  }

  getCurrentUserRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded = jwtDecode<DecodedToken>(token);
      return decoded.role;
    } catch {
      return null;
    }
  }
}
