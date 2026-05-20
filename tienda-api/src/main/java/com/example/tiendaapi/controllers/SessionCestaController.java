package com.example.tiendaapi.controllers;

import com.example.tiendaapi.dtos.SessionCestaAddItemRequestDTO;
import com.example.tiendaapi.dtos.SessionCestaCheckoutRequestDTO;
import com.example.tiendaapi.dtos.SessionCestaCheckoutResponseDTO;
import com.example.tiendaapi.dtos.SessionCestaDTO;
import com.example.tiendaapi.services.SessionCestaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cesta")
public class SessionCestaController {

    @Autowired
    private SessionCestaService sessionCestaService;

    @PostMapping("/productos")
    public ResponseEntity<SessionCestaDTO> addProducto(
            @RequestBody @Valid SessionCestaAddItemRequestDTO request,
            Authentication authentication) {
        return ResponseEntity.ok(sessionCestaService.addItem(authentication.getName(), request));
    }

    @GetMapping
    public ResponseEntity<SessionCestaDTO> getCesta(Authentication authentication) {
        return ResponseEntity.ok(sessionCestaService.getCesta(authentication.getName()));
    }

    @DeleteMapping("/productos/{productoId}")
    public ResponseEntity<SessionCestaDTO> removeProducto(
            @PathVariable Integer productoId,
            Authentication authentication) {
        return ResponseEntity.ok(sessionCestaService.removeItem(authentication.getName(), productoId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCesta(Authentication authentication) {
        sessionCestaService.clearCesta(authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<SessionCestaCheckoutResponseDTO> checkout(
            @RequestBody @Valid SessionCestaCheckoutRequestDTO request,
            Authentication authentication) {
        return ResponseEntity.ok(sessionCestaService.checkout(authentication.getName(), request));
    }
}
