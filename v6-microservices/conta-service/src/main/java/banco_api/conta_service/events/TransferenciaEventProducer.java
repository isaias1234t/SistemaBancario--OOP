package banco_api.conta_service.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaEventProducer {

    private final KafkaTemplate<String, TransferenciaRealizadaEvent> kafkaTemplate;

    public TransferenciaEventProducer(KafkaTemplate<String, TransferenciaRealizadaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarTransferencia(TransferenciaRealizadaEvent transferenciaRealizadaEvent){
        kafkaTemplate.send("transferencia-realizada", transferenciaRealizadaEvent);
    }

}
