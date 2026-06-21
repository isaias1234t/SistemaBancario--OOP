package banco_api.cliente_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("PF")
public class PessoaFisica extends Pessoa {
    private String nome;

    private String cpf;

    public PessoaFisica(String telefone, String cpf, String nome) {
        super(telefone);
        this.cpf = cpf;
        this.nome = nome;
    }
}
