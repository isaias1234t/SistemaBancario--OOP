package banco_api.service;

import banco_api.dto.LoginDTO;
import banco_api.dto.RegistroDTO;
import banco_api.exception.ClienteNaoEncontradoException;
import banco_api.exception.CredenciaisInvalidasException;
import banco_api.model.Usuario;
import banco_api.repository.UsuarioRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static banco_api.model.Role.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void deveRegistrarUsuarioComSucesso(){
        RegistroDTO dto = new RegistroDTO("usuario@gmail.com", "usuario", USER);
        Usuario usuario = new Usuario("usuario@gmail.com", "usuario", USER);
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuario);
        Usuario usuarioCriado = authService.registrar(dto);

        assertNotNull(usuarioCriado);

    }

    @Test
    void deveLogarComSucesso(){
        Usuario usuario = new Usuario("usuario@gmail.com", "usuario", USER);
        when(usuarioRepository.findByEmail("usuario@gmail.com"))
                .thenReturn(Optional.of(usuario));
        LoginDTO dto = new LoginDTO("usuario@gmail.com", "usuario");
            when (passwordEncoder.matches("usuario", "usuario"))
                    .thenReturn(true);
            when(jwtService.gerarToken("usuario@gmail.com"))
                    .thenReturn("token-falso");

            String resultado = authService.login(dto);
            assertNotNull(resultado);
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoEncontrado(){
        when(usuarioRepository.findByEmail("usuario@gmail.com"))
                .thenReturn(Optional.empty());

        LoginDTO dto = new LoginDTO("usuario@gmail.com", "usuario");

        assertThrows(CredenciaisInvalidasException.class, () ->{
            authService.login(dto);
        });
    }

    @Test
    void deveLancarExcecaoQuandoSenhaIncorreta(){
        Usuario usuarioEsperado = new Usuario("usuario@gmail.com", "usuario", USER);
        when(usuarioRepository.findByEmail("usuario@gmail.com"))
                .thenReturn(Optional.of(usuarioEsperado));
        when(passwordEncoder.matches("usuario123", "usuario"))
                .thenReturn(false);

        LoginDTO dto = new LoginDTO("usuario@gmail.com", "usuario123");

        assertThrows(CredenciaisInvalidasException.class, () ->{
            authService.login(dto);
        });


    }





}
