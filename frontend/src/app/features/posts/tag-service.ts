import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { Tag } from '../models/tag';

@Injectable({
  providedIn: 'root',
})
export class TagService {
  private apiUrl = `${environment.apiUrl}/api/tags`;
  constructor(private http: HttpClient) {}

  getAllTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(`${this.apiUrl}`);
  }
}
