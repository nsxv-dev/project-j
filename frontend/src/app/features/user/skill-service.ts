import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Skill } from '../models/skill';

@Injectable({
  providedIn: 'root',
})
export class SkillService {
  private apiUrl = `${environment.apiUrl}/api/skills`;

  constructor(private http: HttpClient) {}

  getAllSkills() {
    return this.http.get<Skill[]>(this.apiUrl);
  }
}
