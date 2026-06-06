package banco_api.controller;

import banco_api.dto.CriarPessoaFisicaDTO;
import banco_api.dto.CriarPessoaJuridicaDTO;
import banco_api.model.Pessoa;
import banco_api.model.PessoaFisica;
import banco_api.model.PessoaJuridica;
import banco_api.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // GETS

    @GetMapping("/juridicas")
    public List<PessoaJuridica> listarPessoasJuridicas(){
        return pessoaService.listarPessoasJuridicas();
    }

    @GetMapping("/fisicas")
    public List <PessoaFisica> listarPessoasFisicas(){
        return pessoaService.listarPessoasFisicas();
    }

    @GetMapping("/{id}")
    public Pessoa buscarPorId(@PathVariable Long id){
        return pessoaService.buscarPessoa(id);
    }

    // POSTS


    @PostMapping("/fisica")
    public ResponseEntity<PessoaFisica> criarPessoaFisica(@Valid @RequestBody CriarPessoaFisicaDTO pessoaFisicaDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pessoaService.criarPessoaFisica(pessoaFisicaDTO));
    }

    @PostMapping("/juridica")
    public ResponseEntity<PessoaJuridica> criarPessoaJuridica(@Valid @RequestBody CriarPessoaJuridicaDTO pessoaJuridicaDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pessoaService.criarPessoaJuridica(pessoaJuridicaDTO));
    }





}












