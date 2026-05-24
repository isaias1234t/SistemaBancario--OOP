package banco_api.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarContaCorrenteDTO {
    @Positive(message = "O limite deve ser maior que zero")
    private double limite;
    @Positive(message = "O ID não pode ser negativo.")
    private int clienteId;
    @Positive(message = "O numero deve ser maior que zero.")
    private int numero;
}
