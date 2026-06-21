package banco_api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("PJ")
public class PessoaJuridica extends Pessoa{
    private String razaoSocial;

    private String cnpj;

    public PessoaJuridica(String telefone, String razaoSocial, String cnpj) {
        super(telefone);
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }
}
