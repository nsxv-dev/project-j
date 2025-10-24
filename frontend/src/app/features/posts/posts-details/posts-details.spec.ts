import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsDetails } from './posts-details';

describe('PostsDetails', () => {
  let component: PostsDetails;
  let fixture: ComponentFixture<PostsDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostsDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostsDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
