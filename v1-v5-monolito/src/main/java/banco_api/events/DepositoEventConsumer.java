package banco_api.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DepositoEventConsumer {
    @KafkaListener(topics = "deposito-realizado", groupId = "banco-api-group")
    public void processarDeposito(DepositoRealizadoEvent depositoRealizadoEvent){
        log.info("Depósito realizado com sucesso para a conta: {}. Valor: R${}. Hora: {}",
                depositoRealizadoEvent.numero(),
                depositoRealizadoEvent.valor(),
                depositoRealizadoEvent.momento());
    }




}
