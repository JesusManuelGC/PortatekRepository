package com.example.tiendaapi.repositories;

import com.example.tiendaapi.entities.ProductoEnCesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoEnCestaRepository extends JpaRepository<ProductoEnCesta, Integer> {
}
