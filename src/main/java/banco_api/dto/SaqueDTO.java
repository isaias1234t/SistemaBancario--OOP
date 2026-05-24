package banco_api.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SaqueDTO {
    @Positive(message = "O valor deve ser maior que zero.")
    private double valor;
}
