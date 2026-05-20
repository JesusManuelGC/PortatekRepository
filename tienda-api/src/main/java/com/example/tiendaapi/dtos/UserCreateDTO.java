package com.example.tiendaapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;
    
    private String nombre;
    private String dni;
    private String pais;
    private String ciudad;
    private String numCalle;
}
