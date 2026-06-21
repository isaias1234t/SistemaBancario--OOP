package banco_api.conta_service.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DepositoRealizadoEvent(Long numero, BigDecimal valor, LocalDateTime momento) {

}
