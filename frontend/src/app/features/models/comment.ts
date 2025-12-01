export interface Comment {
  id?: number;
  content: string;
  createdAt?: string;
  authorId: string;
  authorDisplayName: string;
  authorAvatarUrl: string;
}
