package banco_api.controller;

import banco_api.dto.*;
import banco_api.model.ContaBancaria;
import banco_api.model.Transacao;
import banco_api.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ContaController {
    private final BancoService bancoService;

    public ContaController(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    //GETS
    @GetMapping("/contas")
    public ResponseEntity <List<ContaBancaria>> listarContas(){
        return
                ResponseEntity.ok(bancoService.listarContas());
    }

    @GetMapping("/contas/{id}/extrato")
    public ResponseEntity <List<Transacao>> mostrarExtrato(@PathVariable int id){
        ContaBancaria conta = bancoService.buscarConta(id);
        return ResponseEntity.ok(conta.exibirExtrato());
    }

    // POSTS
    @PostMapping("/contas/corrente")
    public ResponseEntity <ContaBancaria> adicionarContaCorrente(@Valid @RequestBody CriarContaCorrenteDTO contaCorrenteDTO) {
         return ResponseEntity.status(CREATED).body(bancoService.criarContaCorrente(contaCorrenteDTO));
    }

    @PostMapping("/contas/poupanca")
    public  ResponseEntity <ContaBancaria> adicionarContaPoupanca(@Valid @RequestBody CriarContaPoupancaDTO contaPoupancaDTO){
        return ResponseEntity.status(CREATED).body(bancoService.criarContaPoupanca(contaPoupancaDTO));
    }

    @PostMapping("/contas/{id}/saque")
    public ResponseEntity <ContaBancaria> saque(@PathVariable int id, @Valid @RequestBody SaqueDTO saqueDTO){
        ContaBancaria conta = bancoService.buscarConta(id);
        conta.sacar(saqueDTO.getValor());
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/contas/{id}/transferencia")
    public ResponseEntity<ContaBancaria> transferencia(@PathVariable int id, @Valid @RequestBody TransferenciaDTO transferenciaDTO){
        ContaBancaria contaOrigem = bancoService.buscarConta(id);
        ContaBancaria contaDestino = bancoService.buscarConta(transferenciaDTO.getNumeroContaDestino());
        contaOrigem.transferir(contaDestino, transferenciaDTO.getValor());
        return ResponseEntity.ok(contaOrigem);
    }

    @PostMapping("/contas/{id}/deposito")
    public ResponseEntity<ContaBancaria> deposito(@PathVariable int id, @Valid @RequestBody DepositoDTO depositoDTO){
        ContaBancaria conta = bancoService.buscarConta(id);
        conta.depositar(depositoDTO.getValor());
        return ResponseEntity.ok(conta);
    }
}
