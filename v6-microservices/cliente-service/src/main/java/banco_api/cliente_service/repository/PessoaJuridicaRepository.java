package banco_api.cliente_service.repository;

import banco_api.cliente_service.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaJuridicaRepository extends JpaRepository <PessoaJuridica, Long> {
}
