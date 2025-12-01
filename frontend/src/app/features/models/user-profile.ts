import { Skill } from './skill';

export interface UserProfile {
  id: number;
  displayName: string;
  avatarUrl: string;
  email: string;
  role: string;
  skills: Skill[];
  githubUrl: string;
  linkedinUrl: string;
}
