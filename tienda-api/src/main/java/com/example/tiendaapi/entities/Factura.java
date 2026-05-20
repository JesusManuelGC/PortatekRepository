package com.example.tiendaapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_factura")
    private Integer codFactura;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "fecha_factura")
    private LocalDate fechaFactura;

    @Column(name = "forma_pago", length = 25)
    private String formaPago; // 'Tarjeta', 'Bizum', 'Paypal'

    @Column(name = "importe_total")
    private Integer importeTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_pedidos", referencedColumnName = "cod_pedidos")
    private Pedido pedido;
}
