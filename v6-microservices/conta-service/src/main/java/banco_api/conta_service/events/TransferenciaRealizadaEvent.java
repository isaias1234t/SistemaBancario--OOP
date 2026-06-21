package banco_api.conta_service.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferenciaRealizadaEvent(Long numeroContaOrigem,Long numeroContaDestino, BigDecimal valor, LocalDateTime momento) {
}
