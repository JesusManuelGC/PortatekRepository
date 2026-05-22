package com.example.tiendaapi.services;

import com.example.tiendaapi.dtos.AuthRequestDTO;
import com.example.tiendaapi.dtos.AuthResponseDTO;
import com.example.tiendaapi.dtos.GithubLoginRequest;
import com.example.tiendaapi.entities.Role;
import com.example.tiendaapi.entities.User;
import com.example.tiendaapi.repositories.RoleRepository;
import com.example.tiendaapi.repositories.UserRepository;
import com.example.tiendaapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${github.client.id:}")
    private String githubClientId;

    @Value("${github.client.secret:}")
    private String githubClientSecret;

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtUtil.generateToken(user.getUsername(), roles, user.getCodUsuario());

        return new AuthResponseDTO(token, user.getUsername(), roles, user.getCodUsuario());
    }

    public AuthResponseDTO authenticateGithub(GithubLoginRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        
        // 1. Obtener Access Token
        String tokenUrl = "https://github.com/login/oauth/access_token";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", githubClientId);
        params.add("client_secret", githubClientSecret);
        params.add("code", request.getCode());
        
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
        Map<String, String> tokenBody = tokenResponse.getBody();
        
        if (tokenBody == null || !tokenBody.containsKey("access_token")) {
            throw new RuntimeException("Error obteniendo token de GitHub. Revisa el client_id y client_secret");
        }
        String accessToken = tokenBody.get("access_token");

        // 2. Obtener Info del Usuario
        String userUrl = "https://api.github.com/user";
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        HttpEntity<String> userRequestEntity = new HttpEntity<>(userHeaders);
        
        ResponseEntity<Map> userResponse = restTemplate.exchange(userUrl, HttpMethod.GET, userRequestEntity, Map.class);
        Map<String, Object> userBody = userResponse.getBody();
        
        if (userBody == null || !userBody.containsKey("login")) {
             throw new RuntimeException("Error obteniendo informacion del usuario de GitHub");
        }
        
        String githubUsername = (String) userBody.get("login");
        String name = (String) userBody.get("name");
        String email = (String) userBody.get("email");
        
        // 3. Crear o actualizar usuario en nuestra BD
        Optional<User> optionalUser = userRepository.findByUsername(githubUsername);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
            user.setUsername(githubUsername);
            user.setEmail(email != null ? email : githubUsername + "@github.com");
            user.setNombre(name != null ? name : githubUsername);
            user.setPassword(""); // Oauth no necesita pass
            user.setEnabled(true);
            
            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() ->
                    roleRepository.save(new Role("ROLE_USER")));
            user.setRoles(Collections.singleton(userRole));
            user = userRepository.save(user);
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String jwtToken = jwtUtil.generateToken(user.getUsername(), roles, user.getCodUsuario());
        
        return new AuthResponseDTO(jwtToken, user.getUsername(), roles, user.getCodUsuario());
    }
}
