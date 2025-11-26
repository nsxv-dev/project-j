import { Component } from '@angular/core';
import { Post } from '../../models/post';
import { PostService } from '../post-service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { catchError, of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconButton } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { PostsList } from '../posts-list/posts-list';
import { CommentsDetails } from '../../comments/comments-details/comments-details';

@Component({
  selector: 'app-posts-details',
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatIconButton,
    MatChipsModule,
    CommentsDetails
  ],
  templateUrl: './posts-details.html',
  styleUrl: './posts-details.scss'
})
export class PostsDetails {
  post: Post | null = null;
  isLoading: boolean = true;
  errorMessage: string = "";

    constructor(private route: ActivatedRoute, private postService: PostService) {}
    ngOnInit(): void {
    const postId = this.route.snapshot.paramMap.get('id');
    if (postId) {
      this.postService.getPostById(+postId)
        .pipe(
          catchError(() => {
            this.errorMessage = 'Post not found.';
            this.isLoading = false;
            return of(null);
          })
        )
        .subscribe((data) => {
          if (data) {
            this.post = data;
          }
          this.isLoading = false;
        });
    } else {
      this.errorMessage = 'Invalid post ID.';
      this.isLoading = false;
    }
  }
}
