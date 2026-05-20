import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { TranslatePipe } from '../../shared/translate.pipe';

@Component({
  selector: 'app-github-callback',
  standalone: true,
  imports: [CommonModule, TranslatePipe],
  template: `
    <div class="container my-5 py-5 text-center">
      <div class="spinner-border text-primary" role="status" style="width: 3rem; height: 3rem;">
        <span class="visually-hidden">Cargando...</span>
      </div>
      <h4 class="mt-4 text-white">Iniciando sesión con GitHub...</h4>
      <p *ngIf="error" class="text-danger mt-3">{{ error }}</p>
    </div>
  `
})
export class GithubCallbackComponent implements OnInit {
  error = '';

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const code = params['code'];
      if (code) {
        this.authService.loginWithGithub(code).subscribe({
          next: () => {
            this.router.navigate(['/']);
          },
          error: (err) => {
            console.error('Error en login con github:', err);
            this.error = 'Hubo un error al iniciar sesión con GitHub. Redirigiendo...';
            setTimeout(() => this.router.navigate(['/login']), 3000);
          }
        });
      } else {
        this.error = 'No se recibió el código de autorización.';
        setTimeout(() => this.router.navigate(['/login']), 3000);
      }
    });
  }
}
