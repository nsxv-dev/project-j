import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './shared/components/layout/navbar/navbar';
import { WsTest } from './features/notifications/ws-test/ws-test';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar, WsTest],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('frontend');
}
