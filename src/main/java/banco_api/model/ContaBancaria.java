package banco_api.model;
import banco_api.exception.SaldoInsuficienteException;
import banco_api.exception.ValorInvalidoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    protected double saldo;
    private boolean ativa;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "conta_numero")
    private List<Transacao> transacoes;

    public ContaBancaria(Pessoa pessoa){
    this.pessoa = pessoa;
    this.saldo = 0;
    this.ativa = true;
    this.transacoes = new ArrayList<>();
    }

    //FUNÇÕES
    public void depositar(double valor){
        if(valor <= 0){
            throw new ValorInvalidoException("Erro, valor inválido! o valor a ser depositado DEVE ser maior que 0 (zero)!");
        }
        this.saldo += valor;
        registrarTransacao(TipoTransacao.DEPOSITO, valor);
    }
    public abstract void sacar(double valor);

    public void transferir (ContaBancaria destino, double valor){
        if(valor <= 0) {
            throw new ValorInvalidoException("Erro, valor inválido! o valor a ser transferido DEVE ser maior que 0 (zero)!");
        }
        if(valor > this.getSaldo()){
            throw new SaldoInsuficienteException(String.format("Erro! Seu saldo atualmente é de: R$%.2f, e sua tentativa de transferência é de R$%.2f. Por favor, insira um valor até R$%.2f.",
            this.getSaldo(), valor, this.getSaldo()));
        }
        this.sacar(valor);
        destino.depositar(valor);
    }
    public List<Transacao> exibirExtrato() {
        return transacoes;
    }


    protected void registrarTransacao(TipoTransacao tipo, double valor){
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
