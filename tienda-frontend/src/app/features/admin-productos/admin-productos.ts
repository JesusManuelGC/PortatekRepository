import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../core/services/producto.service';
import { AuthService } from '../../core/services/auth.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';
import { Producto } from '../../models/types';
import { environment } from '../../../environment/environments';

declare var bootstrap: any;

@Component({
  selector: 'app-admin-productos',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe, TranslatePipe],
  templateUrl: './admin-productos.html',
  styleUrls: ['./admin-productos.css']
})
export class AdminProductosComponent implements OnInit {
  productos: Producto[] = [];
  currentProducto: Producto = { descripcion: '', tipoProducto: 'placa base', categoriaProducto: 'componente', stock: 0, precioUnitario: 0 };
  selectedFile: File | null = null;
  editing = false;
  private modal: any;

  constructor(
    private productoService: ProductoService,
    private authService: AuthService,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.productoService.productos$.subscribe(productos => {
      this.productos = productos;
    });
    this.productoService.getProductos();
    this.modal = new bootstrap.Modal(document.getElementById('productoModal'));
  }

  openModal(p?: Producto): void {
    if (p) {
      this.currentProducto = { ...p };
      this.editing = true;
    } else {
      this.currentProducto = { descripcion: '', tipoProducto: 'placa base', categoriaProducto: 'componente', stock: 0, precioUnitario: 0 };
      this.editing = false;
    }
    this.modal.show();
  }

  editProducto(producto: Producto): void {
    this.openModal(producto);
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files && input.files.length > 0 ? input.files[0] : null;
  }

  saveProducto(): void {
    const obs = this.editing && this.currentProducto.codProducto
      ? this.productoService.updateProducto(this.currentProducto.codProducto, this.currentProducto, this.selectedFile)
      : this.productoService.createProducto(this.currentProducto, this.selectedFile);

    obs.subscribe(() => {
      this.productoService.refreshProductos();
      this.modal.hide();
    });
  }

  deleteProducto(id: number): void {
    if (confirm('¿Seguro que quieres eliminar este producto?')) {
      this.productoService.deleteProducto(id).subscribe(() => {
        this.productoService.refreshProductos();
      });
    }
  }

  getImageUrl(imagen: string | undefined): string {
    return imagen ? `${environment.uploadsUrl}/${imagen}` : 'assets/images/default-product.png';
  }

  getBadgeClass(tipo: string): string {
    const classes: Record<string, string> = {
      'placa base': 'bg-primary-subtle text-primary',
      'tarjeta gráfica': 'bg-danger-subtle text-danger',
      'procesador': 'bg-success-subtle text-success',
      'disco duro': 'bg-info-subtle text-info',
      'refrigeración': 'bg-warning-subtle text-warning',
      'ram': 'bg-secondary-subtle text-secondary',
      'torre/caja PC': 'bg-dark-subtle text-dark',
      'fuente de alimentación': 'bg-purple-subtle text-purple',
      'PC sobremesa': 'bg-indigo-subtle text-indigo',
      'portátil': 'bg-teal-subtle text-teal',
      'monitor': 'bg-cyan-subtle text-cyan',
      'teclado': 'bg-pink-subtle text-pink',
      'ratón': 'bg-orange-subtle text-orange'
    };
    return classes[tipo] || 'bg-light-subtle text-dark';
  }
}
