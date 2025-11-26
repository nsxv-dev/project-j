import { Routes } from '@angular/router';
import { PostsList } from './features/posts/posts-list/posts-list';
import { PostsDetails } from './features/posts/posts-details/posts-details';
import { Login } from './core/auth/login/login';
import { authGuard } from './core/auth/guards/auth-guard';
import { Register } from './core/auth/register/register';
import { PostsForm } from './features/posts/posts-form/posts-form';
import { Profile } from './features/user/profile/profile';

export const routes: Routes = [
    { path: 'posts', component: PostsList, canActivate: [authGuard] },
    { path: 'posts/:id', component: PostsDetails },
    { path: 'login', component: Login },
    { path: 'register', component: Register },
    {path: 'add-post', component: PostsForm},
    {path: 'user/:id', component: Profile},
    {path: 'user/me', component: Profile},
    {path: '', component: PostsList},
];
