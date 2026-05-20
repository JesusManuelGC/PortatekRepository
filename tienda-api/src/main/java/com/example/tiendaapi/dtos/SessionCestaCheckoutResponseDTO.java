package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCestaCheckoutResponseDTO {
    private Integer codPedido;
    private Integer codFactura;
    private String username;
    private String formaPago;
    private LocalDate fechaFactura;
    private Integer totalItems;
    private Integer totalPrecio;
    private List<SessionCestaItemDTO> productos;
}
