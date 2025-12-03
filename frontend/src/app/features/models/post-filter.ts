export interface PostFilter {
  keyword?: string; // Search keyword for title/description
  tagIds?: number[]; // List of selected tag IDs
  status?: string; // "OPEN", "CLOSED", or empty for all
}
