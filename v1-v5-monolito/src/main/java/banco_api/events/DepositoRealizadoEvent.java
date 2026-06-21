package banco_api.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DepositoRealizadoEvent(Long numero, BigDecimal valor, LocalDateTime momento) {

}
