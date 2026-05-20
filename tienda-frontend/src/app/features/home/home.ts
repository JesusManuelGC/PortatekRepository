import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductoService } from '../../core/services/producto.service';
import { CartService } from '../../core/services/cart.service';
import { AuthService } from '../../core/services/auth.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';
import { Producto } from '../../models/types';
import { environment } from '../../../environment/environments';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, RouterModule, TranslatePipe],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {
  ultimosProductos: Producto[] = [];

  constructor(
    private productoService: ProductoService,
    private cartService: CartService,
    private authService: AuthService,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.productoService.getProductos().subscribe(productos => {
      this.ultimosProductos = [...productos]
        .sort((a, b) => (b.codProducto || 0) - (a.codProducto || 0))
        .slice(0, 6);
    });
  }

  addToCart(producto: Producto): void {
    this.cartService.addToCart(producto);
  }

  getImageUrl(imagen: string | undefined): string {
    if (imagen) {
      return `${environment.uploadsUrl}/${imagen}`;
    }
    return 'assets/images/default-product.png';
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}
