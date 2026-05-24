package banco_api.model;

import banco_api.exception.SaldoInsuficienteException;
import banco_api.exception.ValorInvalidoException;

public class ContaPoupanca extends ContaBancaria{
    private double taxaRendimento;

    public ContaPoupanca(int numero, Cliente cliente, double taxaRendimento) {
        super(numero, cliente);
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
