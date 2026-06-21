package banco_api.model;

import banco_api.exception.SaldoInsuficienteException;
import banco_api.exception.ValorInvalidoException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@DiscriminatorValue("CORRENTE")
public class ContaCorrente extends ContaBancaria {
    private BigDecimal limite;

    public ContaCorrente(Pessoa pessoa, BigDecimal limite) {
        super(pessoa);
        this.limite = limite;
    }

    @Override
    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Valor inválido.");
        }
        if (valor.compareTo(this.saldo.add(limite)) > 0) {
            throw new SaldoInsuficienteException("Seu saldo e limite são insuficientes.");
        }
        this.saldo = this.saldo.subtract(valor);
        registrarTransacao(TipoTransacao.SAQUE, valor);

    }
}
