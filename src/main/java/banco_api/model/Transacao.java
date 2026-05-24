package banco_api.model;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class Transacao {
    private TipoTransacao tipo;
    private double valor;
    private LocalDateTime data;

    public Transacao(TipoTransacao tipo, double valor){
        this.tipo = tipo;
        this.valor = valor;
        this.data= LocalDateTime.now();
    }
    @Override
    public String toString() {

        return tipo +
                " | Valor: R$ " + valor +
                " | Data: " + data;
    }


}
