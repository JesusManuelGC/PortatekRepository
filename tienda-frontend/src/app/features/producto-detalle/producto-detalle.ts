import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { ProductoService } from '../../core/services/producto.service';
import { CartService } from '../../core/services/cart.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';
import { Producto } from '../../models/types';
import { environment } from '../../../environment/environments';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-producto-detalle',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, RouterModule, TranslatePipe],
  templateUrl: './producto-detalle.html',
  styleUrls: ['./producto-detalle.css']
})
export class ProductoDetalleComponent implements OnInit {
  producto: Producto | null = null;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productoService: ProductoService,
    private cartService: CartService,
    private notificationService: NotificationService,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productoService.getProductoById(+id).subscribe({
        next: (producto) => {
          this.producto = producto;
          this.loading = false;
        },
        error: () => {
          this.loading = false;
          this.router.navigate(['/productos']);
        }
      });
    }
  }

  getImageUrl(imagen: string | undefined): string {
    return imagen ? `${environment.uploadsUrl}/${imagen}` : 'assets/images/default-product.png';
  }

  getCategoriaBadgeClass(categoria: string): string {
    const classes: Record<string, string> = {
      'componente': 'bg-primary-subtle text-primary',
      'ordenador': 'bg-success-subtle text-success',
      'periférico': 'bg-info-subtle text-info'
    };
    return classes[categoria] || 'bg-secondary-subtle text-secondary';
  }

  addToCart(): void {
    if (this.producto) {
      const success = this.cartService.addToCart(this.producto);
      if (!success) {
        this.notificationService.notify('Stock insuficiente para este producto', 'error');
      } else {
        this.notificationService.notify('Producto añadido al carrito');
      }
    }
  }
}
