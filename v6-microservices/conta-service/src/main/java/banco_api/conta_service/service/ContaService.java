package banco_api.conta_service.service;

import banco_api.conta_service.dto.CriarContaCorrenteDTO;
import banco_api.conta_service.dto.CriarContaPoupancaDTO;
import banco_api.conta_service.dto.TransferenciaDTO;
import banco_api.conta_service.exception.ContaNaoEncontradaException;
import banco_api.conta_service.model.ContaBancaria;
import banco_api.conta_service.model.ContaCorrente;
import banco_api.conta_service.model.ContaPoupanca;
import banco_api.conta_service.repository.ContaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import banco_api.conta_service.events.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ContaService {

    //Construtor do repository e Kafka
    public ContaService(ContaRepository contaRepository, DepositoEventProducer depositoEventProducer, SaqueEventProducer saqueEventProducer, TransferenciaEventProducer transferenciaEventProducer) {
        this.contaRepository = contaRepository;
        this.depositoEventProducer = depositoEventProducer;
        this.saqueEventProducer = saqueEventProducer;
        this.transferenciaEventProducer = transferenciaEventProducer;
    }

    private final ContaRepository contaRepository;

    //Injeção do Kafka
    private final DepositoEventProducer depositoEventProducer;
    private final SaqueEventProducer saqueEventProducer;
    private final TransferenciaEventProducer transferenciaEventProducer;

    //Métodos para criação de contas
    public ContaCorrente criarContaCorrente(CriarContaCorrenteDTO contaCorrente){
        ContaCorrente contaCorrenteCriada = new ContaCorrente(contaCorrente.getPessoaId(), contaCorrente.getLimite());
        contaRepository.save(contaCorrenteCriada);
        //Logs
        log.info("Conta Corrente criada com sucesso! Conta: {}", contaCorrenteCriada);
        return contaCorrenteCriada;
    }


    public ContaPoupanca criarContaPoupanca(CriarContaPoupancaDTO contaPoupancaDTO){
        ContaPoupanca contaPoupancaCriada = new ContaPoupanca(contaPoupancaDTO.getPessoaId(), contaPoupancaDTO.getTaxaRendimento());
        contaRepository.save(contaPoupancaCriada);
        //Logs
        log.info("Conta Poupança criada com sucesso! Conta: {}", contaPoupancaCriada);
        return contaPoupancaCriada;
    }

    //Buscar contas
    public ContaBancaria buscarConta(Long numero) {
        return contaRepository.findById(numero)
                .orElseThrow(() -> new ContaNaoEncontradaException
                        (String.format("Erro! Conta não encontrada!")));
    }

    //Buscar contas com Lock
    public ContaBancaria buscarContaComLock(Long numero){
        return contaRepository.findByNumero(numero)
                .orElseThrow(() -> new ContaNaoEncontradaException
                        (String.format("Erro! Conta não encontrada!")));
    }

    //Listar contas
    public List<ContaBancaria> listarContas() {
        return contaRepository.findAll();
    }


    //Transações

    @Transactional
    public ContaBancaria depositar(Long numero, BigDecimal valor){
        ContaBancaria contaQueRecebeDeposito = buscarContaComLock(numero);
        contaQueRecebeDeposito.depositar(valor);
        contaRepository.save(contaQueRecebeDeposito);

        //Kafka
        DepositoRealizadoEvent depositoRealizadoEvent = new DepositoRealizadoEvent(contaQueRecebeDeposito.getNumero(),
                valor,
                LocalDateTime.now());
        depositoEventProducer.publicarDeposito(depositoRealizadoEvent);

        //Logs
        log.info("Depósito realizado com sucesso para a conta: {}. Valor: {}", numero, valor);
        return contaQueRecebeDeposito;
    }

    @Transactional
    public ContaBancaria sacar(Long numero, BigDecimal valor){
        ContaBancaria contaQueSaca = buscarContaComLock(numero);
        contaQueSaca.sacar(valor);
        contaRepository.save(contaQueSaca);

        //Kafka
        SaqueRealizadoEvent saqueRealizadoEvent = new SaqueRealizadoEvent(contaQueSaca.getNumero(),
                valor,
                LocalDateTime.now());
        saqueEventProducer.publicarSaque(saqueRealizadoEvent);

        //Logs
        log.info("Saque realizado com sucesso na conta: {}. Valor: {}", numero, valor);
        return contaQueSaca;
    }

    @Transactional
    public ContaBancaria transferir (TransferenciaDTO dto, Long numero,BigDecimal valor){
        Long primeiro;
        Long segundo;
        if (numero < dto.getNumeroContaDestino()){
            primeiro = numero;
            segundo = dto.getNumeroContaDestino();
        } else {
            primeiro = dto.getNumeroContaDestino();
            segundo = numero;
        }
        ContaBancaria contaA = buscarContaComLock(primeiro);
        ContaBancaria contaB = buscarContaComLock(segundo);

        ContaBancaria origem;
        ContaBancaria destino;

        if (contaA.getNumero().equals(numero)){
            origem = contaA;
            destino = contaB;
        }
        else{
            origem = contaB;
            destino = contaA;
        }

        origem.transferir(destino, valor);
        contaRepository.save(origem);
        contaRepository.save(destino);
        //Kafka
        TransferenciaRealizadaEvent transferenciaRealizadaEvent = new TransferenciaRealizadaEvent(origem.getNumero(),
                destino.getNumero(),
                valor,
                LocalDateTime.now());
        transferenciaEventProducer.publicarTransferencia(transferenciaRealizadaEvent);
        //Logs
        log.info("Transferência realizada com sucesso! Conta origem: {}. Conta Destino: {}", origem, destino);
        return origem;
    }

}
