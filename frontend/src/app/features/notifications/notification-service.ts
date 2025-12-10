import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
// import SockJS from 'sockjs-client/dist/sockjs';
// import SockJS from 'sockjs-client'; // use default import from sockjs-client
import SockJS from 'sockjs-client';
@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private client!: Client;

  constructor() {}

  /**
   * Connect to the WebSocket server with JWT token
   * @param token JWT token from backend
   */
  connect(token: string) {
    // Create SockJS client
    const socket = new SockJS(`http://localhost:8080/ws?token=${token}`);

    // Configure STOMP client
    this.client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000, // auto reconnect after 5s
      debug: (msg) => console.log(new Date(), msg),
    });

    // Called after successful connection
    this.client.onConnect = () => {
      console.log('CONNECTED!');

      // Subscribe to user-specific notifications
      this.client.subscribe('/user/queue/notifications', (message: IMessage) => {
        console.log('Notification received:', message.body);

        // TODO: replace with UI toast or notification panel
      });
      this.client.subscribe('/topic/test', (msg) => console.log(msg.body));
    };

    // STOMP errors
    this.client.onStompError = (frame) => {
      console.error('STOMP ERROR', frame);
    };
    this.client.debug = (msg: string) => console.log(new Date(), msg);

    // Activate connection
    this.client.activate();
  }

  /**
   * Disconnect the WebSocket
   */
  disconnect() {
    if (this.client && this.client.active) {
      this.client.deactivate();
      console.log('Disconnected from WebSocket');
    }
  }
}
