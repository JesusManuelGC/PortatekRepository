package com.example.tiendaapi.mappers;

import com.example.tiendaapi.dtos.PedidoDTO;
import com.example.tiendaapi.entities.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) return null;
        PedidoDTO dto = new PedidoDTO();
        dto.setCodPedidos(pedido.getCodPedidos());
        dto.setImporte(pedido.getImporte());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaPrevistaEntrega(pedido.getFechaPrevistaEntrega());
        dto.setFechaEntrega(pedido.getFechaEntrega());
        if (pedido.getUser() != null) {
            dto.setCodUsuario(pedido.getUser().getCodUsuario());
        }
        return dto;
    }
}
