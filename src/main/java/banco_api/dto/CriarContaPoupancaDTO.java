package banco_api.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter

public class CriarContaPoupancaDTO {
    @Positive(message = "O número deve ser maior que zero.")
    private Long numero;
    @Positive(message = "A taxa de rendimento deve ser maior que zero.")
    private double taxaRendimento;
    @Positive(message = "O ID não pode ser negativo.")
    private Long pessoaId;

}
