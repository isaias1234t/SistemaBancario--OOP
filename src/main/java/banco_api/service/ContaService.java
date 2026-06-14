package banco_api.service;

import banco_api.dto.CriarContaCorrenteDTO;
import banco_api.dto.CriarContaPoupancaDTO;
import banco_api.dto.TransferenciaDTO;
import banco_api.exception.ContaNaoEncontradaException;
import banco_api.model.*;
import banco_api.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContaService {

    public ContaService(PessoaService pessoaService, ContaRepository contaRepository) {
        this.pessoaService = pessoaService;
        this.contaRepository = contaRepository;
    }

    private final PessoaService pessoaService;
    private final ContaRepository contaRepository;

    public ContaCorrente criarContaCorrente(CriarContaCorrenteDTO contaCorrente){
        Pessoa pessoa = pessoaService.buscarPessoa(contaCorrente.getPessoaId());
        ContaCorrente contaCorrenteCriada = new ContaCorrente(pessoa, contaCorrente.getLimite());
        contaRepository.save(contaCorrenteCriada);
        log.info("Conta Corrente criada com sucesso! Conta: {}", contaCorrenteCriada);
        return contaCorrenteCriada;
    }


    public ContaPoupanca criarContaPoupanca(CriarContaPoupancaDTO contaPoupancaDTO){
        Pessoa pessoa = pessoaService.buscarPessoa(contaPoupancaDTO.getPessoaId());
        ContaPoupanca contaPoupancaCriada = new ContaPoupanca(pessoa, contaPoupancaDTO.getTaxaRendimento());
        contaRepository.save(contaPoupancaCriada);
        log.info("Conta Poupança criada com sucesso! Conta: {}", contaPoupancaCriada);
        return contaPoupancaCriada;
    }


    public ContaBancaria buscarConta(Long numero) {
        return contaRepository.findById(numero)
                .orElseThrow(() -> new ContaNaoEncontradaException
                        (String.format("Erro! Conta não encontrada!")));
    }

    public List<ContaBancaria> listarContas() {
        return contaRepository.findAll();
    }
    public ContaBancaria depositar(Long numero, double valor){
        ContaBancaria contaQueRecebeDeposito = buscarConta(numero);
        contaQueRecebeDeposito.depositar(valor);
        contaRepository.save(contaQueRecebeDeposito);
        log.info("Depósito realizado com sucesso para a conta: {}. Valor: {}", numero, valor);
        return contaQueRecebeDeposito;
    }

    public ContaBancaria sacar(Long numero, double valor){
        ContaBancaria contaQueSaca = buscarConta(numero);
        contaQueSaca.sacar(valor);
        contaRepository.save(contaQueSaca);
        log.info("Saque realizado com sucesso na conta: {}. Valor: {}", numero, valor);
        return contaQueSaca;
    }

    public ContaBancaria transferir (TransferenciaDTO dto, Long numero,double valor){
        ContaBancaria origem = buscarConta(numero);
        ContaBancaria destino = buscarConta(dto.getNumeroContaDestino());
        origem.transferir(destino, valor);
        contaRepository.save(origem);
        contaRepository.save(destino);
        log.info("Transferência realizada com sucesso! Conta origem: {}. Conta Destino: {}", origem, destino);
        return origem;
    }

}
