import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Notification {
  message: string;
  type: 'success' | 'error' | 'warning' | 'info';
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationsSubject = new BehaviorSubject<Notification[]>([]);

  getNotifications(): Observable<Notification[]> {
    return this.notificationsSubject.asObservable();
  }

  notify(message: string, type: 'success' | 'error' | 'warning' | 'info' = 'success'): void {
    const notification: Notification = { message, type };
    const current = this.notificationsSubject.value;
    this.notificationsSubject.next([...current, notification]);

    // Remove after 3 seconds
    setTimeout(() => {
      this.notificationsSubject.next(this.notificationsSubject.value.filter(n => n !== notification));
    }, 3000);
  }

  connect(): void {
    console.log('NotificationService connected');
  }

  disconnect(): void {
    console.log('NotificationService disconnected');
  }
}
