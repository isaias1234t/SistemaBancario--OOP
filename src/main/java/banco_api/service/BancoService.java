package banco_api.service;

import banco_api.dto.CriarClienteDTO;
import banco_api.dto.CriarContaCorrenteDTO;
import banco_api.dto.CriarContaPoupancaDTO;
import banco_api.exception.ClienteNaoEncontradoException;
import banco_api.exception.ContaNaoEncontradaException;
import banco_api.model.Cliente;
import banco_api.model.ContaBancaria;
import banco_api.model.ContaCorrente;
import banco_api.model.ContaPoupanca;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BancoService {

    private List<Cliente> clientes;
    private List<ContaBancaria> contas;

    public BancoService() {
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
    }
// CLIENTES
    public Cliente criarCliente(CriarClienteDTO clienteDTO) {
        Cliente clienteCriado = new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getTelefone());
        clientes.add(clienteCriado);
        return clienteCriado;
    }

    public Cliente buscarCliente(int id){
        for(Cliente clienteAtual : clientes) {
            if (clienteAtual.getId() == id) {
                return clienteAtual;
            }
        }
        throw new ClienteNaoEncontradoException(String.format("Erro! O cliente com ID %d não foi encontrado!", id));
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }

    // CONTAS
    public ContaCorrente criarContaCorrente(CriarContaCorrenteDTO contaCorrente){
        Cliente cliente = buscarCliente(contaCorrente.getClienteId());
        ContaCorrente contaCorrenteCriada = new ContaCorrente(contaCorrente.getNumero(), cliente, contaCorrente.getLimite());
        contas.add(contaCorrenteCriada);
        cliente.adicionarConta(contaCorrenteCriada);
        return contaCorrenteCriada;
    }


    public ContaPoupanca criarContaPoupanca(CriarContaPoupancaDTO contaPoupancaDTO){
        Cliente cliente = buscarCliente(contaPoupancaDTO.getClienteId());
        ContaPoupanca contaPoupancaCriada = new ContaPoupanca(contaPoupancaDTO.getNumero(), cliente, contaPoupancaDTO.getTaxaRendimento());
        cliente.adicionarConta(contaPoupancaCriada);
        contas.add(contaPoupancaCriada);
        return contaPoupancaCriada;
    }


    public ContaBancaria buscarConta(int numero) {
        for (ContaBancaria conta : contas) {

            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        throw new ContaNaoEncontradaException(String.format("Erro! Conta com o número: %d não encontrada!", numero));
    }

    public List<ContaBancaria> listarContas() {
            return contas;
    }



}
