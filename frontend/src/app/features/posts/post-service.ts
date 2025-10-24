import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Post } from '../models/post.model';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService {
    private apiUrl = `${environment.apiUrl}/api/posts`;
  constructor(private http: HttpClient){}

  // Fetch all posts from the API
  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.apiUrl).pipe(
      map(posts => this.sortPostsByDate(posts))  // Sort the posts before returning
    );
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`);
  }

  getPostsOfLoggedInUser(): Observable<Post[]>{
    return this.http.get<Post[]>(`${this.apiUrl}/my`).pipe(
      map(posts => this.sortPostsByDate(posts))
    )
  }

  createPost(post: Post):Observable<Post>{
    return this.http.post<Post>(this.apiUrl, post);
  }

  // Function to sort posts by date in descending order (newest first)
  private sortPostsByDate(posts: Post[]): Post[] {
    return posts.sort((a, b) => {
      const dateA = new Date(a.createdAt);
      const dateB = new Date(b.createdAt);
      return dateB.getTime() - dateA.getTime();
    });
  }
}
