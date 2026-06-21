package banco_api.conta_service.service;

import banco_api.conta_service.dto.CriarContaCorrenteDTO;
import banco_api.conta_service.dto.CriarContaPoupancaDTO;
import banco_api.conta_service.dto.TransferenciaDTO;
import banco_api.conta_service.exception.ContaNaoEncontradaException;
import banco_api.conta_service.model.ContaBancaria;
import banco_api.conta_service.model.ContaCorrente;
import banco_api.conta_service.model.ContaPoupanca;
import banco_api.conta_service.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import banco_api.conta_service.events.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ContaService {

    public ContaService(ContaRepository contaRepository, DepositoEventProducer depositoEventProducer, SaqueEventProducer saqueEventProducer, TransferenciaEventProducer transferenciaEventProducer) {
        this.contaRepository = contaRepository;
        this.depositoEventProducer = depositoEventProducer;
        this.saqueEventProducer = saqueEventProducer;
        this.transferenciaEventProducer = transferenciaEventProducer;
    }

    private final ContaRepository contaRepository;

    //Kafka
    private final DepositoEventProducer depositoEventProducer;
    private final SaqueEventProducer saqueEventProducer;
    private final TransferenciaEventProducer transferenciaEventProducer;

    public ContaCorrente criarContaCorrente(CriarContaCorrenteDTO contaCorrente){
        ContaCorrente contaCorrenteCriada = new ContaCorrente(contaCorrente.getPessoaId(), contaCorrente.getLimite());
        contaRepository.save(contaCorrenteCriada);
        log.info("Conta Corrente criada com sucesso! Conta: {}", contaCorrenteCriada);
        return contaCorrenteCriada;
    }


    public ContaPoupanca criarContaPoupanca(CriarContaPoupancaDTO contaPoupancaDTO){
        ContaPoupanca contaPoupancaCriada = new ContaPoupanca(contaPoupancaDTO.getPessoaId(), contaPoupancaDTO.getTaxaRendimento());
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


    public ContaBancaria depositar(Long numero, BigDecimal valor){
        ContaBancaria contaQueRecebeDeposito = buscarConta(numero);
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

    public ContaBancaria sacar(Long numero, BigDecimal valor){
        ContaBancaria contaQueSaca = buscarConta(numero);
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

    public ContaBancaria transferir (TransferenciaDTO dto, Long numero,BigDecimal valor){
        ContaBancaria origem = buscarConta(numero);
        ContaBancaria destino = buscarConta(dto.getNumeroContaDestino());
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
