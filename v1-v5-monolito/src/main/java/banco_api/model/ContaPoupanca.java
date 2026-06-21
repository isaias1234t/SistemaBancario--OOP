package banco_api.model;

import banco_api.exception.SaldoInsuficienteException;
import banco_api.exception.ValorInvalidoException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@Entity
@DiscriminatorValue("POUPANCA")
public class ContaPoupanca extends ContaBancaria{
    private BigDecimal taxaRendimento;

    public ContaPoupanca(Pessoa pessoa, BigDecimal taxaRendimento) {
        super(pessoa);
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <=0) {
            throw new ValorInvalidoException("Valor inválido.");
        }
        if(valor.compareTo(saldo) >0){
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }
        this.saldo = this.saldo.subtract(valor);
        registrarTransacao(TipoTransacao.SAQUE, valor);

    }
    public void render(){
        this.saldo = this.saldo.add(this.saldo.multiply(taxaRendimento));
    }
}
