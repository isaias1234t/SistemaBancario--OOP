package banco_api.controller;

import banco_api.dto.LoginDTO;
import banco_api.dto.RegistroDTO;
import banco_api.model.Usuario;
import banco_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity <Usuario> registrar(@Valid @RequestBody RegistroDTO registrar){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.registrar(registrar));
    }

    @PostMapping("/login")
    public ResponseEntity <Map<String, String>> logar(@Valid @RequestBody LoginDTO logar){
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", authService.login(logar)));
    }



}
