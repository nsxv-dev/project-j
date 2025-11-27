import { Component } from '@angular/core';
import { Post } from '../../models/post';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { PostService } from '../post-service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-posts-my',
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    RouterLink,
    MatPaginator,
  ],
  templateUrl: './posts-my.html',
  styleUrl: './posts-my.scss',
})
export class PostsMy {
  posts: Post[] = [];
  isLoading: boolean = true;
  errorMessage: string = '';
  totalCount: number = 0;
  pageSize: number = 8;
  currentPage: number = 0;

  constructor(private postService: PostService) {}

  ngOnInit() {
    this.loadPosts();
  }

  loadPosts() {
    this.postService.getPostsOfLoggedInUser(this.currentPage, this.pageSize).subscribe({
      next: (data) => {
        this.posts = data.content;
        this.totalCount = data.totalElements;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to load posts.';
        this.isLoading = false;
      },
    });
  }

  onPageChange(event: PageEvent) {
    this.updatePage(event.pageIndex, event.pageSize);
  }

  updatePage(pageIndex: number, pageSize: number) {
    this.currentPage = pageIndex;
    this.pageSize = pageSize;
    this.loadPosts();
  }
}
