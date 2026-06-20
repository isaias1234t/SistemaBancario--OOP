package banco_api.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DepositoEventProducer {

    private final KafkaTemplate<String, DepositoRealizadoEvent> kafkaTemplate;

    public DepositoEventProducer(KafkaTemplate<String, DepositoRealizadoEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }



    public void publicarDeposito(DepositoRealizadoEvent depositoRealizadoEvent){
        kafkaTemplate.send("deposito-realizado", depositoRealizadoEvent);
    }



}
