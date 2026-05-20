import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { CartService } from '../../core/services/cart.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, TranslatePipe],
  templateUrl: './header.html',
  styleUrls: ['./header.css']
})
export class HeaderComponent implements OnInit {
  cartItemCount: number = 0;
  username: string | null = null;
  currentLang: string = 'es';

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private router: Router,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.cartService.getCartItems().subscribe(items => {
      this.cartItemCount = items.reduce((count, item) => count + item.cantidad, 0);
    });
    this.username = this.authService.currentUser?.username || null;
    this.currentLang = this.translateService.getLanguage();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  setLanguage(lang: string): void {
    this.currentLang = lang;
    this.translateService.setLanguage(lang);
  }
}
