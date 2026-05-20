package com.example.tiendaapi.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCestaAddItemRequestDTO {
    @NotNull
    private Integer productoId;

    @NotNull
    @Min(1)
    private Integer cantidad;
}
