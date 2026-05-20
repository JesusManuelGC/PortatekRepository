import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { CartService, CartItem } from '../../core/services/cart.service';
import { AuthService } from '../../core/services/auth.service';
import { ProductoService } from '../../core/services/producto.service';
import { NotificationService } from '../../core/services/notification.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';
import { environment } from '../../../environment/environments';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, RouterModule, TranslatePipe],
  templateUrl: './cart.html',
  styleUrls: ['./cart.css']
})
export class CartComponent implements OnInit {
  items: CartItem[] = [];
  total: number = 0;
  loading = false;

  constructor(
    private cartService: CartService,
    private authService: AuthService,
    private router: Router,
    private productoService: ProductoService,
    private notificationService: NotificationService,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.cartService.getCartItems().subscribe(items => {
      this.items = items;
      this.total = this.cartService.getTotalPrice();
    });
  }

  updateQuantity(item: CartItem, delta: number): void {
    const newCantidad = item.cantidad + delta;
    if (newCantidad > 0 && (item.producto.stock == null || newCantidad <= item.producto.stock)) {
      this.cartService.addToCart(item.producto, delta);
    } else if (newCantidad <= 0) {
      this.removeItem(item.producto.codProducto!);
    }
  }

  canCheckout(): boolean {
    return this.items.every(item => 
      item.producto.stock != null && item.cantidad <= item.producto.stock
    );
  }

  removeItem(id: number): void {
    this.cartService.removeFromCart(id);
  }

  checkout(): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }

    if (!this.canCheckout()) {
      this.notificationService.notify('Stock insuficiente para algunos productos', 'error');
      return;
    }

    this.loading = true;
    this.cartService.syncAndCheckout('Tarjeta').subscribe({
      next: () => {
        this.notificationService.notify('Pedido realizado con exito');
        this.cartService.clearCart();
        this.productoService.refreshProductos();
        setTimeout(() => {
          this.router.navigate(['/productos']);
          this.loading = false;
        }, 500);
      },
      error: (err) => {
        this.notificationService.notify(err?.error?.message || 'No se pudo completar el pedido', 'error');
        this.loading = false;
      }
    });
  }

  getImageUrl(imagen: string | undefined): string {
    return imagen ? `${environment.uploadsUrl}/${imagen}` : 'assets/images/default-product.png';
  }
}
