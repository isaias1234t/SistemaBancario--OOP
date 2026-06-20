package banco_api.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferenciaRealizadaEvent(Long numeroContaOrigem,Long numeroContaDestino, BigDecimal valor, LocalDateTime momento) {
}
