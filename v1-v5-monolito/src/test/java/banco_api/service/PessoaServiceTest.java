package banco_api.service;

import banco_api.dto.CriarPessoaFisicaDTO;
import banco_api.dto.CriarPessoaJuridicaDTO;
import banco_api.exception.ClienteNaoEncontradoException;
import banco_api.model.Pessoa;
import banco_api.model.PessoaFisica;
import banco_api.model.PessoaJuridica;
import banco_api.repository.PessoaFisicaRepository;
import banco_api.repository.PessoaJuridicaRepository;
import banco_api.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    //Mocks
    @Mock
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Mock
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    //Testes
    @Test
    void deveCriarPessoaFisicaComSucesso(){
        CriarPessoaFisicaDTO dto = new CriarPessoaFisicaDTO("16999999999", "12332145611", "Isaías");
        PessoaFisica pfEsperada = new PessoaFisica("16999999999", "12332145611", "Isaías");

        when(pessoaFisicaRepository.save(any(PessoaFisica.class)))
                .thenReturn(pfEsperada);

        PessoaFisica resultado = pessoaService.criarPessoaFisica(dto);

        assertNotNull(resultado);
        assertEquals("Isaías", resultado.getNome());
        }

        @Test
    void deveCriarPessoaJuridicaComSucesso() {
            CriarPessoaJuridicaDTO dto = new CriarPessoaJuridicaDTO("Teste", "12332145612", "132343242");
            PessoaJuridica pjEsperada = new PessoaJuridica("12332145612", "Teste", "132343242");

            when(pessoaJuridicaRepository.save(any(PessoaJuridica.class)))
                    .thenReturn(pjEsperada);

            PessoaJuridica resultado = pessoaService.criarPessoaJuridica(dto);

            assertNotNull(resultado);
            assertEquals("Teste", resultado.getRazaoSocial());
        }

        @Test
    void deveLancarExcecaoQuandoPessoaNaoEncontrada(){
            when(pessoaRepository.findById(99L))
                    .thenReturn(Optional.empty());

            assertThrows(ClienteNaoEncontradoException.class, () -> {
                pessoaService.buscarPessoa(99L);
            });
        }

        @Test
    void deveBuscarPessoaComSucesso(){
            when(pessoaRepository.findById(1L))
                    .thenReturn(Optional.of(new PessoaFisica()));
            Pessoa resultado = pessoaService.buscarPessoa(1L);

        assertNotNull(resultado);
        }




    }


