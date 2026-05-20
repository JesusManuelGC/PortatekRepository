package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Integer codFactura;
    private String descripcion;
    private LocalDate fechaFactura;
    private String formaPago;
    private Integer importeTotal;
    private Integer codPedido;
}
