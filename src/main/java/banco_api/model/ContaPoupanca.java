package banco_api.model;

import banco_api.exception.SaldoInsuficienteException;
import banco_api.exception.ValorInvalidoException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Entity
@DiscriminatorValue("POUPANCA")
public class ContaPoupanca extends ContaBancaria{
    private double taxaRendimento;

    public ContaPoupanca(Pessoa pessoa, double taxaRendimento) {
        super(pessoa);
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor inválido.");
        }
        if(valor > saldo){
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }
        saldo -= valor;
        registrarTransacao(TipoTransacao.SAQUE, valor);

    }
    public void render(){
        saldo += (saldo * taxaRendimento);
    }
}
