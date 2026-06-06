package banco_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@Entity
@Table(name = "transacoes")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
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
