import { Tag } from './tag';

export interface Post {
  id: number;
  title: string;
  description: string;
  tags: Tag[];
  type: string;
  status: string;
  createdAt: string;
  authorId: string;
  authorDisplayName: string;
  authorAvatarUrl: string;
}
