package banco_api.auth_service.service;

import banco_api.auth_service.dto.LoginDTO;
import banco_api.auth_service.dto.RegistroDTO;
import banco_api.auth_service.exception.CredenciaisInvalidasException;
import banco_api.auth_service.model.Role;
import banco_api.auth_service.model.Usuario;
import banco_api.auth_service.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrar(RegistroDTO dto) {
        String senhaCodificada = passwordEncoder.encode(dto.getSenha());
        Role role = dto.getRole() != null ? dto.getRole() : Role.USER;
        Usuario usuario = new Usuario(dto.getEmail(), senhaCodificada, role);
        usuarioRepository.save(usuario);
        log.info("Usuário registrado com sucesso! Usuário: {}", usuario);
        return usuario;
    }

    public String login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException(String.format("Erro! credenciais inválidas.")));
        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException("Erro! credenciais inválidas.");
        }
        log.info("Usuário logado com sucesso! Usuário: {}", usuario);
        return jwtService.gerarToken(usuario.getEmail());
    }
}

