package banco_api.conta_service.model;
import banco_api.conta_service.exception.SaldoInsuficienteException;
import banco_api.conta_service.exception.ValorInvalidoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table (name = "contas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "tipo")
public abstract class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    protected BigDecimal saldo;
    private boolean ativa;
    private Long pessoaId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "conta_numero")
    private List<Transacao> transacoes;

    public ContaBancaria(Long pessoaId){
        this.pessoaId = pessoaId;
        this.saldo = BigDecimal.ZERO;
        this.ativa = true;
        this.transacoes = new ArrayList<>();
    }

    //FUNÇÕES
    public void depositar(BigDecimal valor){
        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("Erro, valor inválido! o valor a ser depositado DEVE ser maior que 0 (zero)!");
        }
        this.saldo = this.saldo.add(valor);
        registrarTransacao(TipoTransacao.DEPOSITO, valor);
    }
    public abstract void sacar(BigDecimal valor);

    public void transferir (ContaBancaria destino, BigDecimal valor){
        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Erro, valor inválido! o valor a ser transferido DEVE ser maior que 0 (zero)!");
        }
        if(valor.compareTo(this.getSaldo()) >0){
            throw new SaldoInsuficienteException(String.format("Erro! Seu saldo atualmente é de: R$%.2f, e sua tentativa de transferência é de R$%.2f. Por favor, insira um valor até R$%.2f.",
            this.getSaldo(), valor, this.getSaldo()));
        }
        this.sacar(valor);
        destino.depositar(valor);
    }
    public List<Transacao> exibirExtrato() {
        return transacoes;
    }


    protected void registrarTransacao(TipoTransacao tipo, BigDecimal valor){
        Transacao t = new Transacao(tipo, valor);
        transacoes.add(t);
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() +
                " | Número: " + numero +
                " | Saldo: R$ " + saldo;
    }
}
