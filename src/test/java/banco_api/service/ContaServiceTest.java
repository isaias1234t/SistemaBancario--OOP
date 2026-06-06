package banco_api.service;

import banco_api.dto.CriarContaCorrenteDTO;
import banco_api.dto.CriarContaPoupancaDTO;
import banco_api.exception.ClienteNaoEncontradoException;
import banco_api.exception.ContaNaoEncontradaException;
import banco_api.model.*;
import banco_api.repository.ContaRepository;
import banco_api.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {
    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private ContaService contaService;

    @Test
    void deveCriarContaCorrenteComSucesso(){
        PessoaFisica pessoa = new PessoaFisica("16999999999", "123.456.789-00", "Isaías");
        when (pessoaService.buscarPessoa(1L))
                .thenReturn(pessoa);


        CriarContaCorrenteDTO dto = new CriarContaCorrenteDTO(10, 1L, 161L);
        ContaCorrente contaCorrente = new ContaCorrente(pessoa, 10);

        when(contaRepository.save(any(ContaCorrente.class)))
                .thenReturn(contaCorrente);

        ContaCorrente resultado = contaService.criarContaCorrente(dto);

        assertNotNull(resultado);
    }

    @Test
    void deveCriarContaCorrenteSemSucesso(){
        when (pessoaService.buscarPessoa(99L))
                .thenThrow(new ClienteNaoEncontradoException("Pessoa não encontrada!"));

        CriarContaCorrenteDTO dto = new CriarContaCorrenteDTO(10, 99L, 161L);

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            contaService.criarContaCorrente(dto);
        });
    }

    @Test
    void deveCriarContaPoupancaComSucesso(){
        PessoaFisica pessoa = new PessoaFisica("16999999999", "987.456.789-00", "Rodrigo");
        when(pessoaService.buscarPessoa(2L))
                .thenReturn(pessoa);

        CriarContaPoupancaDTO dto = new CriarContaPoupancaDTO(50L, 5L, 2L);
        ContaPoupanca contaPoupanca = new ContaPoupanca(pessoa, 5L);

        when(contaRepository.save(any(ContaPoupanca.class)))
                .thenReturn(contaPoupanca);

        ContaPoupanca resultado = contaService.criarContaPoupanca(dto);

        assertNotNull(resultado);

    }

    @Test
    void deveCriarContaPoupancaSemSucesso(){
        when(pessoaService.buscarPessoa(49L))
                .thenThrow(new ClienteNaoEncontradoException("Pessoa não encontrada!"));

        CriarContaPoupancaDTO dto = new CriarContaPoupancaDTO(132L, 2, 49L);

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            contaService.criarContaPoupanca(dto);
        });
    }

    @Test
    void deveBuscarContaComSucesso(){
        PessoaFisica pessoa = new PessoaFisica("16999999999", "123.456.789-00", "Isaías");
        when(contaRepository.findById(1L))
                .thenReturn(Optional.of(new ContaCorrente(pessoa, 350)));

        ContaBancaria resultado = contaService.buscarConta(1L);

        assertNotNull(resultado);
    }

    @Test
    void deveBuscarContaSemSucesso(){
        when(contaRepository.findById(32L))
                .thenReturn(Optional.empty());

        assertThrows(ContaNaoEncontradaException.class, () ->{
            contaService.buscarConta(32L);
        });
    }

}
