import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { FacturaService } from '../../core/services/factura.service';
import { TranslateService } from '../../core/services/translate.service';
import { TranslatePipe } from '../../shared/translate.pipe';
import { Factura } from '../../models/types';

@Component({
  selector: 'app-facturas',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, DatePipe, TranslatePipe],
  templateUrl: './facturas.html',
  styleUrls: ['./facturas.css']
})
export class FacturasComponent implements OnInit {
  facturas: Factura[] = [];
  loading = true;

  constructor(private facturaService: FacturaService, private translateService: TranslateService) {}

  ngOnInit(): void {
    this.facturaService.getMyFacturas().subscribe({
      next: (facturas) => {
        this.facturas = facturas;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
