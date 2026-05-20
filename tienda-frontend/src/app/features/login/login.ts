import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';
import { environment } from '../../../environment/environments';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, TranslatePipe],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  username = '';
  password = '';
  error = '';
  loading = false;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private translateService: TranslateService
  ) {}

  onLogin(): void {
    this.loading = true;
    this.error = '';
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        this.router.navigate(['/']);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Credenciales incorrectas. Por favor, inténtalo de nuevo.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  loginWithGithub(): void {
    if (!environment.githubClientId) {
      this.error = 'GitHub Client ID no está configurado.';
      return;
    }
    const redirectUri = window.location.origin + '/oauth2/callback/github';
    const githubAuthUrl = `https://github.com/login/oauth/authorize?client_id=${environment.githubClientId}&redirect_uri=${redirectUri}&scope=read:user user:email`;
    window.location.href = githubAuthUrl;
  }
}
