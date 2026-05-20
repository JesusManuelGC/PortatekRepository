package com.example.tiendaapi.repositories;

import com.example.tiendaapi.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    List<Factura> findByPedido_User_UsernameOrderByFechaFacturaDescCodFacturaDesc(String username);
}
