package banco_api.conta_service.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class CriarContaCorrenteDTO {
    @Positive(message = "O limite deve ser maior que zero")
    private BigDecimal limite;
    @Positive(message = "O ID não pode ser negativo.")
    private Long pessoaId;
    @Positive(message = "O numero deve ser maior que zero.")
    private Long numero;
}
