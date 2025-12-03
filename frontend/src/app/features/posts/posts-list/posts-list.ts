import { Component } from '@angular/core';
import { Post } from '../../models/post';
import { PostService } from '../post-service';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterLink } from '@angular/router';
import { PostsForm } from '../posts-form/posts-form';
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { PostsFilter } from '../filter/filter';
import { PostFilter } from '../../models/post-filter';
import { Tag } from '../../models/tag';
import { TagService } from '../tag-service';

@Component({
  selector: 'app-posts-list',
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    RouterLink,
    PostsForm,
    MatPaginator,
    PostsFilter,
  ],
  templateUrl: './posts-list.html',
  styleUrl: './posts-list.scss',
})
export class PostsList {
  posts: Post[] = [];
  isLoading: boolean = true;
  errorMessage: string = '';
  totalCount: number = 0;
  pageSize: number = 8;
  currentPage: number = 0;
  tags: Tag[] = [];

  constructor(private postService: PostService, private tagService: TagService) {}

  ngOnInit() {
    this.loadTags();
    this.loadPosts();
  }

  loadPosts(filter?: PostFilter) {
    this.isLoading = true;
    this.errorMessage = '';

    let request$;

    if (filter && (filter.keyword || (filter.tagIds && filter.tagIds.length) || filter.status)) {
      // Use filter endpoint
      request$ = this.postService.filterPosts(filter, this.currentPage, this.pageSize);
    } else {
      // Load all posts normally
      request$ = this.postService.getAllPosts(this.currentPage, this.pageSize);
    }

    request$.subscribe({
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

  onFilter(filter: PostFilter) {
    this.loadPosts(filter);
  }

  loadTags() {
    this.tagService.getAllTags().subscribe({
      next: (tags) => {
        this.tags = tags; // assign to array bound to filter
      },
      error: () => {
        console.error('Failed to load tags');
      },
    });
  }
}
