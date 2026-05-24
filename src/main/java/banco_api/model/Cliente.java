package banco_api.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor
public class Cliente extends Pessoa{
    private int id;

    @JsonIgnore
    private List<ContaBancaria> contas;

    public Cliente(int id, String nome, String cpf, String telefone) {
        super(nome, cpf, telefone);
        this.id = id;
        this.contas = new ArrayList<>();
    }
    public void adicionarConta(ContaBancaria conta){
        contas.add(conta);
    }
    public List<ContaBancaria> listarContas(){
            return contas;
    }
    @Override
    public String toString() {

        return "Cliente: " + getNome() +
                " | CPF: " + getCpf() +
                " | ID: " + id;
    }
}
