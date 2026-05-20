package com.example.tiendaapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer codUsuario;
    private String nombre;
    private String dni;
    private String pais;
    private String ciudad;
    private String numCalle;
    private String username;
    private String email;
    private Set<String> roles;
}
