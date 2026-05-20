package com.example.tiendaapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCreateDTO {
    @NotBlank
    private String descripcion;
    
    @NotBlank
    private String tipoProducto; // placa base, tarjeta gráfica, etc.
    
    @NotBlank
    private String categoriaProducto; // componente, ordenador, periférico
    
    @NotNull
    private Integer stock;
    
    @NotNull
    private Integer precioUnitario;

    private String imagen;
}
