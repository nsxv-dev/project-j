import { Component } from '@angular/core';
import { Post } from '../../models/post';
import { PostService } from '../post-service';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterLink } from '@angular/router';
import { PostsForm } from "../posts-form/posts-form";

@Component({
  selector: 'app-posts-list',
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    RouterLink,
    PostsForm
  ],
  templateUrl: './posts-list.html',
  styleUrl: './posts-list.scss'
})
export class PostsList {
  posts: Post[] = [];
  isLoading: boolean = true;
  errorMessage: string = "";

  constructor(private postService: PostService){}

  ngOnInit(){
    this.loadPosts();
  }

  loadPosts(){
    this.postService.getAllPosts().subscribe({
      next: (data) =>{
        this.posts = data;
        this.isLoading = false;
      },
      error: (err)=>{
        this.errorMessage = "Failed to load posts.";
        this.isLoading = false;
      }
    })
  }
}
