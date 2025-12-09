import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Tag } from '../../models/tag';
import { PostFilter } from '../../models/post-filter';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { PostStatus } from '../../models/post-status';

@Component({
  selector: 'app-filter',
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule,
  ],
  templateUrl: './filter.html',
  styleUrl: './filter.scss',
})
export class PostsFilter implements OnInit {
  @Input() tags: Tag[] = [];
  @Output() filtered = new EventEmitter<PostFilter>();

  filterForm: FormGroup;

  statusOptions = [
    { value: PostStatus.ALL, label: 'All' },
    { value: PostStatus.OPEN, label: 'Open' },
    { value: PostStatus.CLOSED, label: 'Closed' },
  ];

  constructor(private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      keyword: [''],
      tagIds: [[]],
      status: [PostStatus.ALL],
    });
  }

  ngOnInit(): void {}

  applyFilter() {
    const rawValue = this.filterForm.value;

    const filter: PostFilter = {
      keyword: rawValue.keyword || undefined,
      tagIds: rawValue.tagIds?.length ? rawValue.tagIds : undefined,
      status:
        rawValue.status === PostStatus.ALL || rawValue.status === '' ? undefined : rawValue.status,
    };

    this.filtered.emit(filter);
  }

  clearFilter() {
    this.filterForm.reset({
      keyword: '',
      tagIds: [],
      status: PostStatus.ALL,
    });
    this.applyFilter();
  }
}
