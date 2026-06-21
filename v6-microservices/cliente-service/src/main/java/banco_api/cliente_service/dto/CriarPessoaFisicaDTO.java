package banco_api.cliente_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class CriarPessoaFisicaDTO {
    @NotEmpty(message = "O nome não pode estar vazio")
    private String nome;

    @NotEmpty(message = "O cpf não pode estar vazio")
    private String cpf;

    @NotEmpty(message = "O telefone não pode estar vazio")
    private String telefone;
}
