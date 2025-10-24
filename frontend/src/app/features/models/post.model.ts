export interface Post {
  id: number;
  title: string;
  description: string;
  tags: string[];
  type: string;
  status: string;
  createdAt: string;
  authorId: number;
  authorDisplayName: string;
}
