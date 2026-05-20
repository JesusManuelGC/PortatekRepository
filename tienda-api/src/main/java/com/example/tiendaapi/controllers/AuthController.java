package com.example.tiendaapi.controllers;

import com.example.tiendaapi.dtos.AuthRequestDTO;
import com.example.tiendaapi.dtos.AuthResponseDTO;
import com.example.tiendaapi.dtos.UserCreateDTO;
import com.example.tiendaapi.dtos.UserDTO;
import com.example.tiendaapi.services.AuthService;
import com.example.tiendaapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/github")
    public ResponseEntity<AuthResponseDTO> githubLogin(@RequestBody com.example.tiendaapi.dtos.GithubLoginRequest request) {
        return ResponseEntity.ok(authService.authenticateGithub(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserCreateDTO createDTO) {
        return ResponseEntity.ok(userService.registerUser(createDTO));
    }
}
