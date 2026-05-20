package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer codPedidos;
    private Integer importe;
    private LocalDate fechaPedido;
    private LocalDate fechaPrevistaEntrega;
    private LocalDate fechaEntrega;
    private Integer codUsuario;
    private List<CestaDTO> cestas;
}
