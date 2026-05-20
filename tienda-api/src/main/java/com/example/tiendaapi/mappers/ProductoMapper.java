package com.example.tiendaapi.mappers;

import com.example.tiendaapi.dtos.ProductoCreateDTO;
import com.example.tiendaapi.dtos.ProductoDTO;
import com.example.tiendaapi.entities.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto entity) {
        if (entity == null) return null;
        ProductoDTO dto = new ProductoDTO();
        dto.setCodProducto(entity.getCodProducto());
        dto.setDescripcion(entity.getDescripcion());
        dto.setTipoProducto(entity.getTipoProducto());
        dto.setCategoriaProducto(entity.getCategoriaProducto());
        dto.setStock(entity.getStock());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setImagen(entity.getImagen());
        return dto;
    }

    public Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;
        Producto entity = new Producto();
        entity.setCodProducto(dto.getCodProducto());
        entity.setDescripcion(dto.getDescripcion());
        entity.setTipoProducto(dto.getTipoProducto());
        entity.setCategoriaProducto(dto.getCategoriaProducto());
        entity.setStock(dto.getStock());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setImagen(dto.getImagen());
        return entity;
    }

    public Producto toEntity(ProductoCreateDTO createDTO) {
        if (createDTO == null) return null;
        Producto entity = new Producto();
        entity.setDescripcion(createDTO.getDescripcion());
        entity.setTipoProducto(createDTO.getTipoProducto());
        entity.setCategoriaProducto(createDTO.getCategoriaProducto());
        entity.setStock(createDTO.getStock());
        entity.setPrecioUnitario(createDTO.getPrecioUnitario());
        entity.setImagen(createDTO.getImagen());
        return entity;
    }
}
