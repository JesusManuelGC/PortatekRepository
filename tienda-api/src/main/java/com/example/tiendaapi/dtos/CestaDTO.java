package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CestaDTO {
    private Integer codCesta;
    private Integer cantidad;
    private Integer descuentoPromocion;
    private List<ProductoEnCestaDTO> productos;
}
