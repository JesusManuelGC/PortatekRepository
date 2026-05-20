package com.example.tiendaapi.mappers;

import com.example.tiendaapi.dtos.UserCreateDTO;
import com.example.tiendaapi.dtos.UserDTO;
import com.example.tiendaapi.entities.Role;
import com.example.tiendaapi.entities.User;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setCodUsuario(user.getCodUsuario());
        dto.setNombre(user.getNombre());
        dto.setDni(user.getDni());
        dto.setPais(user.getPais());
        dto.setCiudad(user.getCiudad());
        dto.setNumCalle(user.getNumCalle());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public User toEntity(UserCreateDTO createDTO) {
        if (createDTO == null) return null;
        User user = new User();
        user.setUsername(createDTO.getUsername());
        user.setEmail(createDTO.getEmail());
        user.setPassword(createDTO.getPassword());
        user.setNombre(createDTO.getNombre());
        user.setDni(createDTO.getDni());
        user.setPais(createDTO.getPais());
        user.setCiudad(createDTO.getCiudad());
        user.setNumCalle(createDTO.getNumCalle());
        user.setEnabled(true);
        return user;
    }
}
