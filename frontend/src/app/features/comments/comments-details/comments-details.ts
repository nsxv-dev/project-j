import { Component, Input } from '@angular/core';
import { CommentService } from '../comment-service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Comment } from '../../models/comment';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { CommentsForm } from '../comments-form/comments-form';

@Component({
  selector: 'app-comments-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    FormsModule,
    CommentsForm,
    RouterModule,
  ],
  templateUrl: './comments-details.html',
  styleUrl: './comments-details.scss',
})
export class CommentsDetails {
  comments: Comment[] = [];
  constructor(private commentService: CommentService, private route: ActivatedRoute) {}

  @Input() postId!: number;

  ngOnInit() {
    this.postId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadComments();
  }

  loadComments() {
    this.commentService.getComments(this.postId).subscribe((res) => (this.comments = res));
  }
}
