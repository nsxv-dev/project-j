import { PostStatus } from './post-status';

export interface PostFilter {
  keyword?: string; // Search keyword for title/description
  tagIds?: number[]; // List of selected tag IDs
  status?: PostStatus; // "OPEN", "CLOSED", or empty for all
}
