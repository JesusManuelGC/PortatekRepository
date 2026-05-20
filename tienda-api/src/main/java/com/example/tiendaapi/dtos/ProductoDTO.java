package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer codProducto;
    private String descripcion;
    private String tipoProducto;
    private String categoriaProducto;
    private Integer stock;
    private Integer precioUnitario;
    private String imagen;
}
