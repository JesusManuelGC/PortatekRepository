import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { NotificationService } from '../../core/services/notification.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, TranslatePipe],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent {
  userData = {
    username: '',
    email: '',
    password: '',
    nombre: '',
    dni: '',
    pais: '',
    ciudad: '',
    numCalle: ''
  };
  error = '';
  loading = false;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private notificationService: NotificationService,
    private translateService: TranslateService
  ) {}

  onRegister(): void {
    this.loading = true;
    this.error = '';
    this.authService.register(this.userData).subscribe({
      next: () => {
        this.notificationService.notify('Registro exitoso. ¡Inicia sesión!', 'success');
        this.router.navigate(['/login']);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al registrarse. Inténtalo de nuevo.';
        this.loading = false;
        console.error(err);
      }
    });
  }
}
