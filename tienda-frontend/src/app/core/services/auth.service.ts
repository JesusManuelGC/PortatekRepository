import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environment/environments';
import { configuration } from '../../config/configuration';
import { AuthResponse } from '../../models/types';
import { ProductoService } from './producto.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<AuthResponse | null>(
    JSON.parse(localStorage.getItem(configuration.KEY_USER) || 'null')
  );
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private productoService: ProductoService
  ) {}

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/login`, { username, password })
      .pipe(tap(user => {
        localStorage.setItem(configuration.KEY_TOKEN, user.token);
        localStorage.setItem(configuration.KEY_USER, JSON.stringify(user));
        this.currentUserSubject.next(user);
        this.productoService.getProductos();
      }));
  }

  loginWithGithub(code: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/github`, { code })
      .pipe(tap(user => {
        localStorage.setItem(configuration.KEY_TOKEN, user.token);
        localStorage.setItem(configuration.KEY_USER, JSON.stringify(user));
        this.currentUserSubject.next(user);
        this.productoService.getProductos();
      }));
  }

  register(userData: any): Observable<any> {
    return this.http.post(`${environment.apiUrl}/auth/register`, userData);
  }

  logout(): void {
    localStorage.removeItem(configuration.KEY_TOKEN);
    localStorage.removeItem(configuration.KEY_USER);
    this.currentUserSubject.next(null);
  }

  get currentUser(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return !!this.currentUserSubject.value;
  }

  isAdmin(): boolean {
    const user = this.currentUserSubject.value;
    return !!user && user.roles.includes('ROLE_ADMIN');
  }
}
