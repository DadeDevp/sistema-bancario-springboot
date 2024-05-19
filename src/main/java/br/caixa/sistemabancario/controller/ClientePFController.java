package br.caixa.sistemabancario.controller;

import br.caixa.sistemabancario.dto.Cliente.ClientePFRequestDTO;
import br.caixa.sistemabancario.dto.Cliente.ClientePFResponseDTO;
import br.caixa.sistemabancario.service.ClientePFService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/pf")
public class ClientePFController {

    final private ClientePFService clientePFService;

    public ClientePFController(ClientePFService clientePFService) {
        this.clientePFService = clientePFService;
    }

    @PostMapping
    public ResponseEntity<ClientePFResponseDTO> inserir(@RequestBody @Valid ClientePFRequestDTO clientePFRequestDTO) {
        ClientePFResponseDTO clientePFResponseDTO = clientePFService.inserirClientePF(clientePFRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientePFResponseDTO);
    }

    @PutMapping("{clienteId}")
    public ResponseEntity<ClientePFResponseDTO> atualizar(@PathVariable("clienteId") String id,
                                                          @RequestBody @Valid ClientePFRequestDTO clientePFRequestDTO) {
        ClientePFResponseDTO clientePFResponseDTO = clientePFService.atualizarClientePF(id, clientePFRequestDTO);
        return ResponseEntity.ok(clientePFResponseDTO);
    }

    @DeleteMapping("{clienteId}")
    public ResponseEntity<ClientePFResponseDTO> excluir(@PathVariable("clienteId") String id){
        clientePFService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{clienteId}")
    public ResponseEntity<ClientePFResponseDTO> buscarPorId(@PathVariable("clienteId") String id){
        ClientePFResponseDTO clientePFResponseDTO = clientePFService.buscarClientePFPorId(id);
        return ResponseEntity.ok(clientePFResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientePFResponseDTO>> buscarTodosClientesPF(){
        return ResponseEntity.ok(clientePFService.buscarTodosClientesPF());
    }

}
