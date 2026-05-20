import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, from, Observable, switchMap, concatMap, toArray, Subscription } from 'rxjs';
import { environment } from '../../../environment/environments';
import { CheckoutResponse, Producto } from '../../models/types';
import { ProductoService } from './producto.service';

export interface CartItem {
  producto: Producto;
  cantidad: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService implements OnDestroy {
  private apiUrl = `${environment.apiUrl}/cesta`;
  private cartItemsSubject = new BehaviorSubject<CartItem[]>(
    JSON.parse(localStorage.getItem('cart_items') || '[]')
  );
  private productosSubscription?: Subscription;

  constructor(
    private http: HttpClient,
    private productoService: ProductoService
  ) {
    this.productosSubscription = this.productoService.productos$.subscribe(productos => {
      this.updateCartItemsStock(productos);
    });
  }

  ngOnDestroy(): void {
    this.productosSubscription?.unsubscribe();
  }

  private updateCartItemsStock(productos: Producto[]): void {
    const currentItems = this.cartItemsSubject.value;
    let updated = false;
    
    const itemsWithUpdatedStock = currentItems.map(item => {
      const updatedProducto = productos.find(p => p.codProducto === item.producto.codProducto);
      if (updatedProducto && updatedProducto.stock !== item.producto.stock) {
        updated = true;
        return { ...item, producto: { ...updatedProducto } };
      }
      return item;
    });

    if (updated) {
      this.updateCart(itemsWithUpdatedStock);
    }
  }

  getCartItems(): Observable<CartItem[]> {
    return this.cartItemsSubject.asObservable();
  }

  addToCart(producto: Producto, cantidad: number = 1): boolean {
    const currentItems = this.cartItemsSubject.value;
    const existingItem = currentItems.find(item => item.producto.codProducto === producto.codProducto);
    
    let totalCantidad = existingItem ? existingItem.cantidad + cantidad : cantidad;
    
    if (producto.stock != null && totalCantidad > producto.stock) {
      return false;
    }

    if (existingItem) {
      existingItem.cantidad = totalCantidad;
    } else {
      currentItems.push({ producto, cantidad });
    }

    this.updateCart(currentItems);
    return true;
  }

  removeFromCart(codProducto: number): void {
    const updatedItems = this.cartItemsSubject.value.filter(item => item.producto.codProducto !== codProducto);
    this.updateCart(updatedItems);
  }

  clearCart(): void {
    this.updateCart([]);
  }

  getTotalPrice(): number {
    return this.cartItemsSubject.value.reduce(
      (total, item) => total + (item.producto.precioUnitario * item.cantidad), 
      0
    );
  }

  syncAndCheckout(formaPago: string = 'Tarjeta'): Observable<CheckoutResponse> {
    const items = this.cartItemsSubject.value.filter(item => item.producto.codProducto);
    return this.http.delete<void>(this.apiUrl).pipe(
      switchMap(() => from(items)),
      concatMap(item => this.http.post(`${this.apiUrl}/productos`, {
        productoId: item.producto.codProducto,
        cantidad: item.cantidad
      })),
      toArray(),
      switchMap(() => this.http.post<CheckoutResponse>(`${this.apiUrl}/checkout`, {
        formaPago,
        fechaPrevistaEntrega: null
      }))
    );
  }

  private updateCart(items: CartItem[]): void {
    localStorage.setItem('cart_items', JSON.stringify(items));
    this.cartItemsSubject.next(items);
  }
}
