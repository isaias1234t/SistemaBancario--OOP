package banco_api.conta_service.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class TransferenciaDTO {
    @Positive(message = "O Valor deve ser maior que zero.")
    private BigDecimal valor;
    @Positive(message = "O número da Conta Destino deve ser maior que zero.\"")
    private Long numeroContaDestino;
}
