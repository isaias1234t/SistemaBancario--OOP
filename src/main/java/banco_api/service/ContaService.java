package banco_api.service;

import banco_api.dto.CriarContaCorrenteDTO;
import banco_api.dto.CriarContaPoupancaDTO;
import banco_api.exception.ContaNaoEncontradaException;
import banco_api.model.*;
import banco_api.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return contaCorrenteCriada;
    }


    public ContaPoupanca criarContaPoupanca(CriarContaPoupancaDTO contaPoupancaDTO){
        Pessoa pessoa = pessoaService.buscarPessoa(contaPoupancaDTO.getPessoaId());
        ContaPoupanca contaPoupancaCriada = new ContaPoupanca(pessoa, contaPoupancaDTO.getTaxaRendimento());
        contaRepository.save(contaPoupancaCriada);
        return contaPoupancaCriada;
    }


    public ContaBancaria buscarConta(Long numero) {
        return contaRepository.findById(numero)
                .orElseThrow(() -> new ContaNaoEncontradaException
                        (String.format("Erro! Conta com o número: %d não encontrada!", numero)));
    }

    public List<ContaBancaria> listarContas() {
        return contaRepository.findAll();
    }
}
