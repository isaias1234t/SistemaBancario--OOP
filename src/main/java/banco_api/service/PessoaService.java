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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    public PessoaService(PessoaFisicaRepository pessoaFisicaRepository, PessoaJuridicaRepository pessoaJuridicaRepository, PessoaRepository pessoaRepository) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    //Pessoas Fisicas
    private final PessoaFisicaRepository pessoaFisicaRepository;

    //Pessoas Juridicas
    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    //Classe mãe
    private final PessoaRepository pessoaRepository;

    //Métodos da classe PessoaFisica

    public PessoaFisica criarPessoaFisica(CriarPessoaFisicaDTO pessoaFisicaDTO){
        PessoaFisica pf = new PessoaFisica(pessoaFisicaDTO.getTelefone(), pessoaFisicaDTO.getCpf(), pessoaFisicaDTO.getNome());
       return pessoaFisicaRepository.save(pf);
    }

    public List<PessoaFisica> listarPessoasFisicas(){
        return pessoaFisicaRepository.findAll();
    }

    //Métodos da classe PessoaJurídica

    public PessoaJuridica criarPessoaJuridica(CriarPessoaJuridicaDTO pessoaJuridicaDTO){
        PessoaJuridica pj = new PessoaJuridica(pessoaJuridicaDTO.getTelefone(), pessoaJuridicaDTO.getRazaoSocial(), pessoaJuridicaDTO.getCnpj());
        return pessoaJuridicaRepository.save(pj);
    }

    public List<PessoaJuridica> listarPessoasJuridicas(){
        return pessoaJuridicaRepository.findAll();
    }

    //Métodos da classe Pessoa
    public Pessoa buscarPessoa(Long id){
        return pessoaRepository.findById(id)
                .orElseThrow(()-> new ClienteNaoEncontradoException(String.format("Pessoa com id %d não encontrada!", id)));
    }
}
