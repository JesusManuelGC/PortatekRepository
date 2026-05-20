import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environments';
import { Factura } from '../../models/types';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {
  private apiUrl = `${environment.apiUrl}/facturas`;

  constructor(private http: HttpClient) {}

  getMyFacturas(): Observable<Factura[]> {
    return this.http.get<Factura[]>(`${this.apiUrl}/me`);
  }
}
