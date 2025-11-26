import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../models/user-profile';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/api/users`;
  
  constructor(private http: HttpClient){}
  
  getProfile(id:string|null){
    return this.http.get<UserProfile>(`${this.apiUrl}/${id}`);
  }
}
