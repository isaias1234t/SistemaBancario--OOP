package banco_api.conta_service.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter

public class CriarContaPoupancaDTO {
    @Positive(message = "O número deve ser maior que zero.")
    private Long numero;
    @Positive(message = "A taxa de rendimento deve ser maior que zero.")
    private BigDecimal taxaRendimento;
    @Positive(message = "O ID não pode ser negativo.")
    private Long pessoaId;

}
