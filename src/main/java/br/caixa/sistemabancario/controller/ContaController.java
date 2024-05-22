package br.caixa.sistemabancario.controller;

import br.caixa.sistemabancario.dto.Conta.ContaResponseDTO;
import br.caixa.sistemabancario.dto.Conta.ContaRequestDTO;
import br.caixa.sistemabancario.service.ContaService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/contas")
public class ContaController {

    final private ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/contacorrente")
    public ResponseEntity<ContaResponseDTO> criarContaCorrente(@RequestBody ContaRequestDTO contaRequestDTO){
        ContaResponseDTO contaResponseDTO = contaService.criarContaCorrente(contaRequestDTO.getDocumento());
        return ResponseEntity.status(HttpStatus.CREATED).body(contaResponseDTO);
    }

    @PostMapping("/contapoupanca")
    public ResponseEntity<ContaResponseDTO> criarContaPoupanca(@RequestBody ContaRequestDTO contaRequestDTO){
        ContaResponseDTO contaResponseDTO = contaService.criarContaPoupanca(contaRequestDTO.getDocumento());
        return ResponseEntity.status(HttpStatus.CREATED).body(contaResponseDTO);
    }

    @PostMapping("/containvestimento")
    public ResponseEntity<ContaResponseDTO> criarContaInvestimento(@RequestBody ContaRequestDTO contaRequestDTO){
        ContaResponseDTO contaResponseDTO = contaService.criarContaInvestimento(contaRequestDTO.getDocumento());
        return ResponseEntity.status(HttpStatus.CREATED).body(contaResponseDTO);
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaResponseDTO> consultarContaPorNumero(@PathVariable Integer numeroConta) {
        ContaResponseDTO contaResponseDTO = contaService.buscarContaPorNumero(numeroConta);
        return ResponseEntity.ok(contaResponseDTO);
    }



}
