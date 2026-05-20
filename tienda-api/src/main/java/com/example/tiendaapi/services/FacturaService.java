package com.example.tiendaapi.services;

import com.example.tiendaapi.dtos.FacturaDTO;
import com.example.tiendaapi.mappers.FacturaMapper;
import com.example.tiendaapi.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaMapper facturaMapper;

    public List<FacturaDTO> getFacturasByUsername(String username) {
        return facturaRepository.findByPedido_User_UsernameOrderByFechaFacturaDescCodFacturaDesc(username).stream()
                .map(facturaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
