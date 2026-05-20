package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEnCestaDTO {
    private Integer codProducto;
    private String descripcionProducto;
    private Integer codCesta;
    private LocalDate fechaSeleccion;
    private Integer precioTotal;
}
