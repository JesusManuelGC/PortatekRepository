package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCestaItemDTO {
    private Integer productoId;
    private String descripcion;
    private Integer precioUnitario;
    private Integer cantidad;
    private Integer subtotal;
}
