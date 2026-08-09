package banco_api.conta_service;

import banco_api.conta_service.dto.CriarContaCorrenteDTO;
import banco_api.conta_service.dto.TransferenciaDTO;
import banco_api.conta_service.model.ContaBancaria;
import banco_api.conta_service.model.ContaCorrente;
import banco_api.conta_service.repository.ContaRepository;
import banco_api.conta_service.service.ContaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@DirtiesContext
@TestPropertySource(locations = "classpath:application-test.properties")
@Tag("integration")
public class ContaConcorrenciaTest {

    //Injeção do service e repository
    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaRepository contaRepository;

    //Testar 10 saques simultâneos
    @Test
    void testDepositosConcorrentes() throws InterruptedException {
        //Criação da conta para teste
        CriarContaCorrenteDTO contaCorrenteDTO = new CriarContaCorrenteDTO(BigDecimal.valueOf(500), 2945L, 122412L);
        ContaCorrente contaCorrenteCriada = contaService.criarContaCorrente(contaCorrenteDTO);

        //Criação do ExecutorService para execução de 10 Threads em loop utilizando FOR
        ExecutorService executor = Executors.newFixedThreadPool(10);

        //loop
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                contaService.depositar(contaCorrenteCriada.getNumero(), BigDecimal.valueOf(100));
            });
        }

        //Término do executor
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        //Atualização do saldo
        ContaBancaria contaCorrenteComSaldoAtualizado = contaRepository.findById(contaCorrenteCriada.getNumero())
                    .orElseThrow();

        //Saldo esperado
        BigDecimal saldoEsperado = BigDecimal.valueOf(1000);

        //Validação do teste
        Assertions.assertEquals(
                0,
                contaCorrenteComSaldoAtualizado.getSaldo().compareTo(saldoEsperado)
        );
    }

    //Testar 10 saques simultâneos
    @Test
    void testSaquesConcorrentes() throws InterruptedException {
        //Criação da conta para teste
        CriarContaCorrenteDTO contaCorrenteDTO = new CriarContaCorrenteDTO(BigDecimal.valueOf(200), 3912L, 41243L);
        ContaCorrente contaCorrenteCriada = contaService.criarContaCorrente(contaCorrenteDTO);

        //Depósito na conta criada para testes
        contaService.depositar(contaCorrenteCriada.getNumero(), BigDecimal.valueOf(1000));

        //Criação do ExecutorService para execução de 10 Threads em loop utilizando FOR
        ExecutorService executor = Executors.newFixedThreadPool(10);

        //loop
        for (int i = 0; i < 10; i++){
            executor.submit(() -> {
                contaService.sacar(contaCorrenteCriada.getNumero(), BigDecimal.valueOf(100));
            });
        }

        //Término do executor
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        //Atualização de saldo
        ContaBancaria contaComSaldoAtualizado = contaRepository.findById(contaCorrenteCriada.getNumero())
                .orElseThrow();

        //Saldo Esperado
        BigDecimal saldoEsperado = BigDecimal.valueOf(0);

        //Validação do teste
        Assertions.assertEquals(
                0,
                contaComSaldoAtualizado.getSaldo().compareTo(saldoEsperado)
        );
    }

    //Testar 10 transferências simultâneas
    @Test
    void testTransacoesConcorrentes() throws InterruptedException {
        //Criação das contas para teste de transferências
        CriarContaCorrenteDTO contaOrigemDTO = new CriarContaCorrenteDTO(BigDecimal.valueOf(300), 204321L, 93143L);
        CriarContaCorrenteDTO contaDestinoDTO = new CriarContaCorrenteDTO(BigDecimal.valueOf(300), 148142L, 12853L);

        ContaCorrente contaOrigem = contaService.criarContaCorrente(contaOrigemDTO);
        ContaCorrente contaDestino = contaService.criarContaCorrente(contaDestinoDTO);

        //Depósito na conta origem para teste de transferências
        contaService.depositar(contaOrigem.getNumero(), BigDecimal.valueOf(1000));

        //Criação do ExecutorService para execução de 10 Threads em loop utilizando FOR
        ExecutorService executor = Executors.newFixedThreadPool(10);

        //Criação do DTO para transferir
        TransferenciaDTO transferencias = new TransferenciaDTO();
        //Set do número da conta destino
        transferencias.setNumeroContaDestino(contaDestino.getNumero());

        //loop
        for (int i = 0; i < 10; i++){
            executor.submit(() -> {
                contaService.transferir(transferencias, contaOrigem.getNumero(), BigDecimal.valueOf(100));
            });
        }

        //Término do executor
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        //Atualização de saldo
        ContaBancaria contaOrigemComSaldoAtualizado = contaRepository.findById(contaOrigem.getNumero())
                .orElseThrow();
        ContaBancaria contaDestinoComSaldoAtualizado = contaRepository.findById(contaDestino.getNumero())
                .orElseThrow();

        //Valores esperados para cada conta
        BigDecimal saldoEsperadoContaOrigem = BigDecimal.valueOf(0.00);
        BigDecimal saldoEsperadoContaDestino = BigDecimal.valueOf(1000.00);

        //Validação do teste
        Assertions.assertEquals(
                0,
                contaOrigemComSaldoAtualizado.getSaldo().compareTo(saldoEsperadoContaOrigem)
        );
        Assertions.assertEquals(
                0,
                contaDestinoComSaldoAtualizado.getSaldo().compareTo(saldoEsperadoContaDestino)
        );
    }

    @Test
    void testTransacoesCruzadas() throws InterruptedException {
        //Criação das contas para teste de transferências
        CriarContaCorrenteDTO contaOrigemDTO = new CriarContaCorrenteDTO(BigDecimal.valueOf(300), 204321L, 93143L);
        CriarContaCorrenteDTO contaDestinoDTO = new CriarContaCorrenteDTO(BigDecimal.valueOf(300), 148142L, 12853L);

        ContaCorrente contaOrigem = contaService.criarContaCorrente(contaOrigemDTO);
        ContaCorrente contaDestino = contaService.criarContaCorrente(contaDestinoDTO);

        //Depósito nas contas para teste de transferências
        contaService.depositar(contaOrigem.getNumero(), BigDecimal.valueOf(1000));
        contaService.depositar(contaDestino.getNumero(), BigDecimal.valueOf(1000));

        //Criação do ExecutorService para execução de 10 Threads em loop utilizando FOR
        ExecutorService executor = Executors.newFixedThreadPool(20);

        //Criação dos DTOs para transferências
        TransferenciaDTO transferenciasOrigemDestino = new TransferenciaDTO();
        TransferenciaDTO transferenciasDestinoOrigem = new TransferenciaDTO();

        //Set do número da conta destino
        transferenciasOrigemDestino.setNumeroContaDestino(contaDestino.getNumero());
        transferenciasDestinoOrigem.setNumeroContaDestino(contaOrigem.getNumero());

        //Loop das transferências da contaOrigem -> contaDestino
        for (int i = 0; i < 10; i++){
            executor.submit(() -> {
                contaService.transferir(transferenciasOrigemDestino, contaOrigem.getNumero(), BigDecimal.valueOf(100));
            });
            //Loop das transferências da contaDestino -> contaOrigem
            executor.submit(() -> {
                contaService.transferir(transferenciasDestinoOrigem, contaDestino.getNumero(), BigDecimal.valueOf(100));
            });
            }

        //Término do executor
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        //Atualização de saldo
        ContaBancaria contaOrigemComSaldoAtualizado = contaRepository.findById(contaOrigem.getNumero())
                .orElseThrow();
        ContaBancaria contaDestinoComSaldoAtualizado = contaRepository.findById(contaDestino.getNumero())
                .orElseThrow();

        //Valores esperados para cada conta
        BigDecimal saldoEsperadoContaOrigem = BigDecimal.valueOf(1000.00);
        BigDecimal saldoEsperadoContaDestino = BigDecimal.valueOf(1000.00);

        //Validação do teste
        Assertions.assertEquals(
                0,
                contaOrigemComSaldoAtualizado.getSaldo().compareTo(saldoEsperadoContaOrigem)
        );
        Assertions.assertEquals(
                0,
                contaDestinoComSaldoAtualizado.getSaldo().compareTo(saldoEsperadoContaDestino)
        );
    }
}
