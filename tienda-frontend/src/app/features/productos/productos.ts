import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { ProductoService } from '../../core/services/producto.service';
import { CartService } from '../../core/services/cart.service';
import { AuthService } from '../../core/services/auth.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';
import { Producto } from '../../models/types';
import { environment } from '../../../environment/environments';
import { Subscription } from 'rxjs';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, RouterModule, TranslatePipe],
  templateUrl: './productos.html',
  styleUrls: ['./productos.css']
})
export class ProductosComponent implements OnInit, OnDestroy {
  productos: Producto[] = [];
  allProductos: Producto[] = [];
  loggedIn = false;
  categoriaSeleccionada: string | null = null;
  tipoSeleccionado: string | null = null;
  private sub?: Subscription;
  private routeSub?: Subscription;

  constructor(
    private productoService: ProductoService,
    private cartService: CartService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private notificationService: NotificationService,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.sub = this.authService.currentUser$.subscribe(user => {
      this.loggedIn = !!user;
      if (!this.loggedIn) {
        this.productos = [];
        this.allProductos = [];
        return;
      }
      this.productoService.productos$.subscribe(productos => {
        this.allProductos = productos;
        this.filtrarProductos();
      });
      this.productoService.getProductos();
    });

    this.routeSub = this.route.paramMap.subscribe(params => {
      this.categoriaSeleccionada = params.get('categoria');
      this.tipoSeleccionado = params.get('tipo');
      this.filtrarProductos();
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
    this.routeSub?.unsubscribe();
  }

  filtrarProductos(): void {
    if (this.tipoSeleccionado) {
      this.productos = this.allProductos.filter(p => p.tipoProducto === this.tipoSeleccionado);
    } else if (this.categoriaSeleccionada) {
      this.productos = this.allProductos.filter(p => p.categoriaProducto === this.categoriaSeleccionada);
    } else {
      this.productos = [...this.allProductos];
    }
  }

  getTituloCategoria(): string {
    if (this.tipoSeleccionado) {
      return this.tipoSeleccionado.charAt(0).toUpperCase() + this.tipoSeleccionado.slice(1);
    }
    if (!this.categoriaSeleccionada) {
      return this.translateService.get('productos.titulo');
    }
    const nombres: Record<string, string> = {
      'componente': this.translateService.get('productos.categoria.componentes'),
      'ordenador': this.translateService.get('productos.categoria.ordenadores'),
      'periférico': this.translateService.get('productos.categoria.perifericos')
    };
    return nombres[this.categoriaSeleccionada] || this.translateService.get('productos.titulo');
  }

  addToCart(producto: Producto): void {
    const success = this.cartService.addToCart(producto);
    if (!success) {
      this.notificationService.notify(this.translateService.get('notificaciones.stockInsuficiente'), 'error');
    } else {
      this.notificationService.notify(this.translateService.get('notificaciones.productoAñadido'));
    }
  }

  getImageUrl(imagen: string | undefined): string {
    return imagen ? `${environment.uploadsUrl}/${imagen}` : 'assets/images/default-product.png';
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}
