export interface Role {
  id: number;
  name: string;
}

export interface User {
  codUsuario?: number;
  username: string;
  nombre?: string;
  dni?: string;
  pais?: string;
  ciudad?: string;
  numCalle?: string;
  roles?: string[];
}

export interface Producto {
  codProducto?: number;
  descripcion: string;
  tipoProducto: 'placa base' | 'tarjeta gráfica' | 'procesador' | 'disco duro' | 'refrigeración' | 'ram' | 'torre/caja PC' | 'fuente de alimentación' | 'PC sobremesa' | 'portátil' | 'monitor' | 'teclado' | 'ratón';
  categoriaProducto: 'componente' | 'ordenador' | 'periférico';
  stock: number;
  precioUnitario: number;
  imagen?: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  roles: string[];
  id: number;
}

export interface Cesta {
  codCesta?: number;
  cantidad: number;
  descuentoPromocion: number;
  codPedidos?: number;
}

export interface Pedido {
  codPedidos?: number;
  importe: number;
  fechaPedido: string;
  fechaPrevistaEntrega: string;
  fechaEntrega?: string;
  codUsuario: number;
}

export interface Factura {
  codFactura: number;
  descripcion: string;
  fechaFactura: string;
  formaPago: string;
  importeTotal: number;
  codPedido: number | null;
}

export interface CheckoutResponse {
  codPedido: number;
  codFactura: number;
  username: string;
  formaPago: string;
  fechaFactura: string;
  totalItems: number;
  totalPrecio: number;
}
