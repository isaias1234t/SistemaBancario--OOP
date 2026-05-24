package banco_api.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TransferenciaDTO {
    @Positive(message = "O Valor deve ser maior que zero.")
    private double valor;
    @Positive(message = "O número da Conta Destino deve ser maior que zero.\"")
    private int numeroContaDestino;
}
