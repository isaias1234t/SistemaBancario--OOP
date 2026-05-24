package banco_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CriarClienteDTO {
    @Positive(message = "O ID deve ser maior que zero.")
    private int id;
    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;
    @NotBlank(message = "O CPF não pode estar vazio")
    private String cpf;
    @NotBlank(message = "O telefone não pode estar vazio")
    private String telefone;



}
