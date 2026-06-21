package banco_api.conta_service.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaqueRealizadoEvent(Long numero, BigDecimal valor, LocalDateTime momento) {
}
