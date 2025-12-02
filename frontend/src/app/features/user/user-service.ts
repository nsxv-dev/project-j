import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../models/user-profile';
import { UserProfileUpdate } from '../models/user-profile-update';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/api/users`;

  constructor(private http: HttpClient) {}

  getProfile(id?: string | null) {
    if (!id || id === 'me') {
      return this.http.get<UserProfile>(`${this.apiUrl}/me`);
    }
    return this.http.get<UserProfile>(`${this.apiUrl}/${id}`);
  }

  // ⭐ Get current user's profile
  getCurrentUserProfile() {
    return this.http.get<UserProfile>(`${this.apiUrl}/me`);
  }

  // ⭐ Update current user's profile
  updateProfile(dto: UserProfileUpdate) {
    return this.http.patch<UserProfile>(`${this.apiUrl}/me`, dto);
  }
}
