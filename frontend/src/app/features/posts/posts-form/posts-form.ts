import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { PostService } from '../post-service';
import { CommonModule } from '@angular/common';
import { MatChipsModule } from '@angular/material/chips';
import { MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { Tag } from '../../models/tag';
import { TagService } from '../tag-service';

@Component({
  selector: 'app-posts-form',
  imports: [
    MatCardModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    CommonModule,
    MatChipsModule,
    MatOptionModule,
    MatButtonModule,
    MatSelectModule,
  ],
  templateUrl: './posts-form.html',
  styleUrl: './posts-form.scss',
})
export class PostsForm {
  @Output() postCreated = new EventEmitter<void>();
  @ViewChild(FormGroupDirective) formGroupDirective!: FormGroupDirective;

  postForm: FormGroup;
  errorMessage: string = '';
  isSubmitting: boolean = false;
  message: string = '';
  postTypes = ['OFFER', 'REQUEST'];
  tagsList: Tag[] = []; // All existing tags fetched from backend

  constructor(private postService: PostService, private tagService: TagService) {
    this.postForm = this.initForm();
  }

  ngOnInit() {
    this.loadTags();
  }

  loadTags() {
    this.tagService.getAllTags().subscribe({
      next: (tags) => (this.tagsList = tags),
      error: (err) => console.error('Failed to load tags', err),
    });
  }

  private initForm(): FormGroup {
    return new FormGroup({
      title: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(255),
      ]),
      description: new FormControl('', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(1000),
      ]),
      type: new FormControl('', Validators.required),
      tags: new FormControl([], Validators.required),
    });
  }
  // Getter for tags FormArray
  get tags(): FormArray {
    return this.postForm.get('tags') as FormArray;
  }

  // Add a new tag
  addTag(tag: string) {
    const tagsArray = this.postForm.get('tags') as FormArray;
    tagsArray.push(
      new FormControl(tag, [Validators.required, Validators.minLength(1), Validators.maxLength(50)])
    );
  }

  // Remove a tag by index
  removeTag(index: number) {
    const tagsArray = this.postForm.get('tags') as FormArray;
    tagsArray.removeAt(index);
  }

  onSubmit() {
    if (this.postForm.invalid) return;

    // Get raw form value
    const formValue = this.postForm.value;

    // Map array of IDs to array of TagDTO objects
    const postData = {
      ...formValue,
      tags: formValue.tags.map((id: number) => ({ id })), // <-- this is key
    };

    this.isSubmitting = true;
    this.postService.createPost(postData).subscribe({
      next: () => {
        this.postForm.reset();
        this.isSubmitting = false;
        this.message = '✅ Post created successfully!';
        this.postCreated.emit();
      },
      error: (err) => {
        console.error(err);
        this.message = '❌ Failed to create post.';
        this.isSubmitting = false;
      },
    });

    this.formGroupDirective.resetForm();
  }
}
