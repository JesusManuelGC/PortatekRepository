package com.example.tiendaapi.mappers;

import com.example.tiendaapi.dtos.FacturaDTO;
import com.example.tiendaapi.entities.Factura;
import org.springframework.stereotype.Component;

@Component
public class FacturaMapper {

    public FacturaDTO toDTO(Factura factura) {
        if (factura == null) return null;
        FacturaDTO dto = new FacturaDTO();
        dto.setCodFactura(factura.getCodFactura());
        dto.setDescripcion(factura.getDescripcion());
        dto.setFechaFactura(factura.getFechaFactura());
        dto.setFormaPago(factura.getFormaPago());
        dto.setImporteTotal(factura.getImporteTotal());
        if (factura.getPedido() != null) {
            dto.setCodPedido(factura.getPedido().getCodPedidos());
        }
        return dto;
    }
}
