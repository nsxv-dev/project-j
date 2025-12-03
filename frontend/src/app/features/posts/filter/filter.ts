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
    { value: '', label: 'All' },
    { value: 'OPEN', label: 'Open' },
    { value: 'CLOSED', label: 'Closed' },
  ];

  constructor(private fb: FormBuilder) {
    // Initialize form
    this.filterForm = this.fb.group({
      keyword: [''],
      tagIds: [[]],
      status: [''],
    });
  }

  ngOnInit(): void {}

  applyFilter() {
    this.filtered.emit({ ...this.filterForm.value });
  }

  clearFilter() {
    this.filterForm.reset({
      keyword: '',
      tagIds: [],
      status: '',
    });
    this.applyFilter();
  }
}
