package banco_api.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaqueEventProducer {

    private final KafkaTemplate<String, SaqueRealizadoEvent> kafkaTemplate;

    public SaqueEventProducer(KafkaTemplate<String, SaqueRealizadoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarSaque (SaqueRealizadoEvent saqueRealizadoEvent){
        kafkaTemplate.send("saque-realizado", saqueRealizadoEvent);
        }
    }
