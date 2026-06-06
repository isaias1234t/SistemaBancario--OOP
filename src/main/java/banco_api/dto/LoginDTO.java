package banco_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class LoginDTO {

    @NotBlank(message = "O e-mail não pode ser vazio.")
    private String email;

    @NotBlank(message = "Digite a senha.")
    private String senha;

}
