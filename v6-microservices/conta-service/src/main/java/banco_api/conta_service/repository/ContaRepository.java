package banco_api.conta_service.repository;

import banco_api.conta_service.model.ContaBancaria;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<ContaBancaria, Long> {
    @Lock (LockModeType.PESSIMISTIC_WRITE)
    public Optional <ContaBancaria> findByNumero (Long numero);
}
