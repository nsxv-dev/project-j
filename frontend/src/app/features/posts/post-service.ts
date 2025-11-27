import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Post } from '../models/post';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { PageResponse } from '../models/page-response';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = `${environment.apiUrl}/api/posts`;
  constructor(private http: HttpClient) {}

  getAllPosts(page: number, size: number): Observable<PageResponse<Post>> {
    return this.http.get<PageResponse<Post>>(this.apiUrl, { params: { page, size } });
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`);
  }

  getPostsOfLoggedInUser(page: number, size: number): Observable<PageResponse<Post>> {
    return this.http.get<PageResponse<Post>>(`${this.apiUrl}/my`, { params: { page, size } });
  }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(this.apiUrl, post);
  }
}
