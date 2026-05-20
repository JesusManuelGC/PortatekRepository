package com.example.tiendaapi.repositories;

import com.example.tiendaapi.entities.Cesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CestaRepository extends JpaRepository<Cesta, Integer> {
}
