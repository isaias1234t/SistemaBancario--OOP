package banco_api.conta_service.repository;

import banco_api.conta_service.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaBancaria, Long> {
}
