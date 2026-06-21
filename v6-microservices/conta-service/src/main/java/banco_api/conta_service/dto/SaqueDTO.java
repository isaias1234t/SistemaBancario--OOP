package banco_api.conta_service.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class SaqueDTO {
    @Positive(message = "O valor deve ser maior que zero.")
    private BigDecimal valor;
}
