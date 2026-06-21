package banco_api.cliente_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class CriarPessoaJuridicaDTO {
    @NotEmpty (message = "A razão social não pode ser vazia.")
    private String razaoSocial;

    @NotEmpty (message = "O cnpj não pode estar vazio.")
    private String cnpj;

    @NotEmpty (message = "O telefone não pode estar vazio.")
    private String telefone;
}
