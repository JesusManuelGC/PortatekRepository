import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './layout/header/header';
import { FooterComponent } from './layout/footer/footer';
import { NotificationsComponent } from './shared/notifications/notifications';
import { ProductoService } from './core/services/producto.service';
import { AuthService } from './core/services/auth.service';
import { TranslateService } from './core/services/translate.service';
import { TranslatePipe } from './shared/translate.pipe';

interface Categoria {
  nombreKey: string;
  valor: string;
  icono: string;
  tipos: { nombreKey: string; valor: string }[];
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent, NotificationsComponent, CommonModule, RouterModule, TranslatePipe],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent implements OnInit {
  title = 'tienda-informática';

  categorias: Categoria[] = [
    {
      nombreKey: 'sidebar.componentes',
      valor: 'componente',
      icono: 'bi-cpu',
      tipos: [
        { nombreKey: 'sidebar.placaBase', valor: 'placa base' },
        { nombreKey: 'sidebar.tarjetaGrafica', valor: 'tarjeta gráfica' },
        { nombreKey: 'sidebar.procesador', valor: 'procesador' },
        { nombreKey: 'sidebar.discoDuro', valor: 'disco duro' },
        { nombreKey: 'sidebar.refrigeracion', valor: 'refrigeración' },
        { nombreKey: 'sidebar.ram', valor: 'ram' },
        { nombreKey: 'sidebar.torreCaja', valor: 'torre/caja PC' },
        { nombreKey: 'sidebar.fuenteAlimentacion', valor: 'fuente de alimentación' }
      ]
    },
    {
      nombreKey: 'sidebar.ordenadores',
      valor: 'ordenador',
      icono: 'bi-pc-display',
      tipos: [
        { nombreKey: 'sidebar.pcSobremesa', valor: 'PC sobremesa' },
        { nombreKey: 'sidebar.portatil', valor: 'portátil' }
      ]
    },
    {
      nombreKey: 'sidebar.perifericos',
      valor: 'periférico',
      icono: 'bi-keyboard',
      tipos: [
        { nombreKey: 'sidebar.monitor', valor: 'monitor' },
        { nombreKey: 'sidebar.teclado', valor: 'teclado' },
        { nombreKey: 'sidebar.raton', valor: 'ratón' }
      ]
    }
  ];

  categoriasAbiertas: Record<string, boolean> = {};

  constructor(
    private productoService: ProductoService,
    private authService: AuthService,
    private translateService: TranslateService
  ) {}

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.productoService.getProductos();
    }
  }

  toggleCategoria(categoriaValor: string): void {
    this.categoriasAbiertas[categoriaValor] = !this.categoriasAbiertas[categoriaValor];
  }
}
