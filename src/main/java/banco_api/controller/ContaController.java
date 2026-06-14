package banco_api.controller;

import banco_api.dto.*;
import banco_api.model.ContaBancaria;
import banco_api.model.Transacao;
import banco_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ContaController {
    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    //GETS
    @GetMapping("/contas")
    public ResponseEntity <List<ContaBancaria>> listarContas(){
        return
                ResponseEntity.ok(contaService.listarContas());
    }

    @GetMapping("/contas/{id}/extrato")
    public ResponseEntity <List<Transacao>> mostrarExtrato(@PathVariable Long id){
        ContaBancaria conta = contaService.buscarConta(id);
        return ResponseEntity.ok(conta.exibirExtrato());
    }

    // POSTS
    @PostMapping("/contas/corrente")
    public ResponseEntity <ContaBancaria> adicionarContaCorrente(@Valid @RequestBody CriarContaCorrenteDTO contaCorrenteDTO) {
         return ResponseEntity.status(CREATED).body(contaService.criarContaCorrente(contaCorrenteDTO));
    }

    @PostMapping("/contas/poupanca")
    public  ResponseEntity <ContaBancaria> adicionarContaPoupanca(@Valid @RequestBody CriarContaPoupancaDTO contaPoupancaDTO){
        return ResponseEntity.status(CREATED).body(contaService.criarContaPoupanca(contaPoupancaDTO));
    }

    @PostMapping("/contas/{id}/saque")
    public ResponseEntity <ContaBancaria> saque(@PathVariable Long id, @Valid @RequestBody SaqueDTO saqueDTO){
        return ResponseEntity.ok(contaService.sacar(id, saqueDTO.getValor()));
    }

    @PostMapping("/contas/{id}/transferencia")
    public ResponseEntity<ContaBancaria> transferencia(@PathVariable Long id, @Valid @RequestBody TransferenciaDTO transferenciaDTO){
        return ResponseEntity.ok(contaService.transferir(transferenciaDTO, id, transferenciaDTO.getValor()));
    }

    @PostMapping("/contas/{id}/deposito")
    public ResponseEntity<ContaBancaria> deposito(@PathVariable Long id, @Valid @RequestBody DepositoDTO depositoDTO){
        return ResponseEntity.ok(contaService.depositar(id, depositoDTO.getValor()));
    }
}
