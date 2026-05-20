package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCestaDTO {
    private String username;
    private List<SessionCestaItemDTO> productos;
    private Integer totalItems;
    private Integer totalPrecio;
}
