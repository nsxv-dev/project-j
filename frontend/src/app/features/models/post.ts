import { PostStatus } from './post-status';
import { Tag } from './tag';

export interface Post {
  id: number;
  title: string;
  description: string;
  tags: Tag[];
  type: string;
  status: PostStatus;
  createdAt: string;
  authorId: string;
  authorDisplayName: string;
  authorAvatarUrl: string;
}
