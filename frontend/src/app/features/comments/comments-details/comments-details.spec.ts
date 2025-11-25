import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentsDetails } from './comments-details';

describe('CommentsDetails', () => {
  let component: CommentsDetails;
  let fixture: ComponentFixture<CommentsDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommentsDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentsDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
