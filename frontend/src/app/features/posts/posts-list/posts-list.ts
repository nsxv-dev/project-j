import { Component, OnInit } from '@angular/core';
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
import { PostStatus } from '../../models/post-status';

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
export class PostsList implements OnInit {
  posts: Post[] = [];
  isLoading: boolean = true;
  errorMessage: string = '';
  totalCount: number = 0;
  pageSize: number = 8;
  currentPage: number = 0;
  tags: Tag[] = [];
  currentFilter?: PostFilter;

  constructor(private postService: PostService, private tagService: TagService) {}

  ngOnInit() {
    this.loadTags();
    this.loadPosts();
  }

  loadPosts(filterChange?: PostFilter) {
    this.isLoading = true;
    this.errorMessage = '';

    // Only reset page if a new filter is applied
    if (filterChange) {
      this.currentFilter = filterChange;
      this.currentPage = 0; // start from first page for new filter
    }

    // Prepare payload for backend
    const payload = {
      keyword: this.currentFilter?.keyword,
      tagIds: this.currentFilter?.tagIds,
      status:
        this.currentFilter?.status === PostStatus.ALL ? undefined : this.currentFilter?.status,
    };

    let request$;

    if (payload.keyword || (payload.tagIds && payload.tagIds.length) || payload.status) {
      request$ = this.postService.filterPosts(payload, this.currentPage, this.pageSize);
    } else {
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
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadPosts();
  }

  onFilter(filter: PostFilter) {
    this.loadPosts(filter); // new filter resets page
  }

  loadTags() {
    this.tagService.getAllTags().subscribe({
      next: (tags) => {
        this.tags = tags;
      },
      error: () => {
        console.error('Failed to load tags');
      },
    });
  }
}
