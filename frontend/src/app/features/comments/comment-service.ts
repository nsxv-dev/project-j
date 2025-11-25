import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Comment } from '../models/comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiUrl = `${environment.apiUrl}/api/posts`;

  constructor(private http: HttpClient) {}

  getComments(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/${postId}/comments`)
  }

  addComment(postId: number, comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(`${this.apiUrl}/${postId}/comments`, comment);
  }
}
