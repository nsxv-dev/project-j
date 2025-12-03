import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { Comment } from '../../models/comment';
import { CommentService } from '../comment-service';
import { ActivatedRoute } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-comments-form',
  imports: [MatCardModule, MatInputModule, FormsModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './comments-form.html',
  styleUrl: './comments-form.scss',
})
export class CommentsForm {
  constructor(private commentService: CommentService, private route: ActivatedRoute) {}

  @Input() postId!: number;
  @Output() commentAdded = new EventEmitter<void>(); // Emit event to parent when comment is added
  @ViewChild(FormGroupDirective) formGroupDirective!: FormGroupDirective;

  commentForm = new FormGroup({
    content: new FormControl('', [Validators.required, Validators.maxLength(1000)]),
  });

  ngOnInit() {
    this.postId = Number(this.route.snapshot.paramMap.get('id'));
  }

  submitComment() {
    if (this.commentForm.invalid) return;
    const newComment: Comment = {
      authorDisplayName: '',
      authorAvatarUrl: '',
      authorId: '',
      content: this.commentForm.value.content ?? '',
    };

    this.commentService.addComment(this.postId, newComment).subscribe(() => {
      this.commentForm.reset();
      this.formGroupDirective.resetForm(); // <-- this resets value + validation + touched/pristine
      this.commentAdded.emit(); // Notify the parent component that a new comment is added
    });
  }
}
