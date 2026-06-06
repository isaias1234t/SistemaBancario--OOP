package banco_api.dto;

import banco_api.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegistroDTO {
    @NotBlank(message = "O e-mail não pode ser vazio.")
    private String email;

    @NotBlank(message = "Digite a senha.")
    private String senha;

    private Role role;

}
