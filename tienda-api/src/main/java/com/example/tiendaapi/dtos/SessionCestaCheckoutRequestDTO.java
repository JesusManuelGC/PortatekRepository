package com.example.tiendaapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCestaCheckoutRequestDTO {
    @NotBlank
    private String formaPago;
    private LocalDate fechaPrevistaEntrega;
}
