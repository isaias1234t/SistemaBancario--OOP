package banco_api.conta_service.events;

import banco_api.conta_service.events.SaqueRealizadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaqueEventConsumer {
    @KafkaListener(topics = "saque-realizado", groupId = "banco-api-group")
    public void processarSaque(SaqueRealizadoEvent saqueRealizadoEvent){
        log.info("Saque efetuado para a conta: {}. Valor: R${}. Hora: {}.",
                saqueRealizadoEvent.numero(),
                saqueRealizadoEvent.valor(),
                saqueRealizadoEvent.momento());
    }
}
