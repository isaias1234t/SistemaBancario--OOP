package banco_api.model;

import banco_api.exception.SaldoInsuficienteException;
import banco_api.exception.ValorInvalidoException;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CORRENTE")
public class ContaCorrente extends ContaBancaria {
    private double limite;

    public ContaCorrente(Pessoa pessoa, double limite) {
        super(pessoa);
        this.limite = limite;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor inválido.");
        }
        if (valor > (saldo + limite)) {
            throw new SaldoInsuficienteException("Seu saldo e limite são insuficientes.");
        }
        saldo -= valor;
        registrarTransacao(TipoTransacao.SAQUE, valor);

    }
}
