import { Component, OnInit } from '@angular/core';
import { Client } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';
// import SockJS from 'sockjs-client/dist/sockjs';
import { NotificationService } from '../notification-service';

@Component({
  selector: 'app-ws-test',
  imports: [],
  templateUrl: './ws-test.html',
  styleUrl: './ws-test.scss',
})
export class WsTest implements OnInit {
  constructor(private notificationService: NotificationService) {}

  ngOnInit() {
    const token = localStorage.getItem('token');
    if (token) {
      this.notificationService.connect(token);
    } else {
      console.error('JWT token not found in localStorage');
    }
  }
}
