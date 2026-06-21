package banco_api.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class DepositoDTO {
    @Positive(message = "O valor deve ser maior que zero.")
    private BigDecimal valor;
}
