package br.caixa.sistemabancario.controller;

import br.caixa.sistemabancario.dto.Cliente.ClientePJRequestDTO;
import br.caixa.sistemabancario.dto.Cliente.ClientePJResponseDTO;
import br.caixa.sistemabancario.service.ClientePJService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/pj")
public class ClientePJController {

    final private ClientePJService clientePJService;

    public ClientePJController(ClientePJService clientePJService) {
        this.clientePJService = clientePJService;
    }

    @PostMapping
    public ResponseEntity<ClientePJResponseDTO> inserir(@RequestBody @Valid ClientePJRequestDTO clientePJRequestDTO) {
        ClientePJResponseDTO ClientePJResponseDTO = clientePJService.inserirClientePJ(clientePJRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientePJResponseDTO);
    }

    @PutMapping("{clienteId}")
    public ResponseEntity<ClientePJResponseDTO> atualizar(@PathVariable("clienteId") String id,
                                                          @RequestBody @Valid ClientePJRequestDTO clientePJRequestDTO) {
        ClientePJResponseDTO ClientePJResponseDTO = clientePJService.atualizarclientePJ(id, clientePJRequestDTO);
        return ResponseEntity.ok(ClientePJResponseDTO);
    }

    @DeleteMapping("{clienteId}")
    public ResponseEntity<ClientePJResponseDTO> excluir(@PathVariable("clienteId") String id){
        clientePJService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{clienteId}")
    public ResponseEntity<ClientePJResponseDTO> buscarPorId(@PathVariable("clienteId") String id){
        ClientePJResponseDTO ClientePJResponseDTO = clientePJService.buscarclientePJPorId(id);
        return ResponseEntity.ok(ClientePJResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientePJResponseDTO>> buscarTodosClientesPJ(){
        return ResponseEntity.ok(clientePJService.buscarTodosClientesPJ());
    }

}
