package com.example.tiendaapi.repositories;

import com.example.tiendaapi.entities.DireccionEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionEnvioRepository extends JpaRepository<DireccionEnvio, Integer> {
}
