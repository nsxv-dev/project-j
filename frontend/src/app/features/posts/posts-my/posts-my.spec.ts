import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsMy } from './posts-my';

describe('PostsMy', () => {
  let component: PostsMy;
  let fixture: ComponentFixture<PostsMy>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostsMy]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostsMy);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
