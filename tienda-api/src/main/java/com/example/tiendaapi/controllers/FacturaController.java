package com.example.tiendaapi.controllers;

import com.example.tiendaapi.dtos.FacturaDTO;
import com.example.tiendaapi.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/me")
    public ResponseEntity<List<FacturaDTO>> getMyFacturas(Authentication authentication) {
        return ResponseEntity.ok(facturaService.getFacturasByUsername(authentication.getName()));
    }
}
