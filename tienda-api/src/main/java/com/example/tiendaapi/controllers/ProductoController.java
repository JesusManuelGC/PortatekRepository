package com.example.tiendaapi.controllers;

import com.example.tiendaapi.dtos.ProductoCreateDTO;
import com.example.tiendaapi.dtos.ProductoDTO;
import com.example.tiendaapi.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAll() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductoDTO> create(
            @RequestPart("producto") @Valid ProductoCreateDTO createDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(productoService.createProducto(createDTO, image));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductoDTO> update(
            @PathVariable Integer id,
            @RequestPart("producto") @Valid ProductoCreateDTO updateDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(productoService.updateProducto(id, updateDTO, image));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
