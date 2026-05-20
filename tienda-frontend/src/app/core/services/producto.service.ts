import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { environment } from '../../../environment/environments';
import { Producto } from '../../models/types';

interface ProductoCreate {
  descripcion: string;
  tipoProducto: string;
  categoriaProducto: string;
  stock: number;
  precioUnitario: number;
  imagen?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = `${environment.apiUrl}/productos`;
  private productosSubject = new BehaviorSubject<Producto[]>([]);
  productos$ = this.productosSubject.asObservable();
  private productosMap: Map<number, Producto> = new Map();

  constructor(private http: HttpClient) {}

  getProductos(): Observable<Producto[]> {
    this.http.get<Producto[]>(this.apiUrl).subscribe(productos => {
      productos.forEach(p => this.productosMap.set(p.codProducto!, p));
      this.productosSubject.next(productos);
    });
    return this.productos$;
  }

  refreshProductos(): void {
    this.http.get<Producto[]>(this.apiUrl).subscribe(productos => {
      productos.forEach(p => this.productosMap.set(p.codProducto!, p));
      this.productosSubject.next(productos);
    });
  }

  getProductoById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  getProductoFromCache(id: number): Producto | undefined {
    return this.productosMap.get(id);
  }

  createProducto(producto: Producto, image: File | null): Observable<Producto> {
    const formData = new FormData();
    
    const createData: ProductoCreate = {
      descripcion: producto.descripcion,
      tipoProducto: producto.tipoProducto,
      categoriaProducto: producto.categoriaProducto,
      stock: producto.stock,
      precioUnitario: producto.precioUnitario,
      imagen: producto.imagen
    };
    
    formData.append('producto', new Blob([JSON.stringify(createData)], { type: 'application/json' }));
    if (image) {
      formData.append('image', image);
    }
    return this.http.post<Producto>(this.apiUrl, formData);
  }

  updateProducto(id: number, producto: Producto, image: File | null): Observable<Producto> {
    const formData = new FormData();
    
    const updateData: ProductoCreate = {
      descripcion: producto.descripcion,
      tipoProducto: producto.tipoProducto,
      categoriaProducto: producto.categoriaProducto,
      stock: producto.stock,
      precioUnitario: producto.precioUnitario,
      imagen: producto.imagen
    };
    
    formData.append('producto', new Blob([JSON.stringify(updateData)], { type: 'application/json' }));
    if (image) {
      formData.append('image', image);
    }
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, formData);
  }

  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
