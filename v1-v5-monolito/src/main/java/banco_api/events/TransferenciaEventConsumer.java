package banco_api.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransferenciaEventConsumer {
    @KafkaListener(topics = "transferencia-realizada", groupId = "banco-api-group")
    public void processarTransferencia(TransferenciaRealizadaEvent transferenciaRealizadaEvent){
        log.info("Transferência realizada com sucesso! Conta origem: {}. Conta Destino: {}. Valor: R${}. Hora: {}.",
                transferenciaRealizadaEvent.numeroContaOrigem(),
                transferenciaRealizadaEvent.numeroContaDestino(),
                transferenciaRealizadaEvent.valor(),
                transferenciaRealizadaEvent.momento());
    }
}
