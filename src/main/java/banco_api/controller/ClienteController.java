package banco_api.controller;

import banco_api.dto.CriarClienteDTO;
import banco_api.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import banco_api.model.Cliente;

@RestController
public class ClienteController {
    private final BancoService bancoService;

    public ClienteController(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    // GETS
    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return bancoService.listarClientes();
    }

    // POSTS

    @PostMapping("/clientes")
    public Cliente adicionarCliente(@Valid @RequestBody CriarClienteDTO clienteDTO){
        return bancoService.criarCliente(clienteDTO);
    }

}












