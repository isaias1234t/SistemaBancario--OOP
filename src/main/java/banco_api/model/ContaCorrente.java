package banco_api.model;

import banco_api.exception.SaldoInsuficienteException;
import banco_api.exception.ValorInvalidoException;

public class ContaCorrente extends ContaBancaria {
    private double limite;

    public ContaCorrente(int numero, Cliente cliente, double limite) {
        super(numero, cliente);
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
