package br.caixa.sistemabancario.controller;

import br.caixa.sistemabancario.dto.Transacao.DepositoRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.SaqueRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.TransferenciaRequestDTO;
import br.caixa.sistemabancario.service.TransacaoPJService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes/pj")
public class TransacaoPJController {

    final private TransacaoPJService transacaoPJService;

    public TransacaoPJController(TransacaoPJService transacaoPJService) {
        this.transacaoPJService = transacaoPJService;
    }

    @PutMapping("/deposito")
    public ResponseEntity depositar(@RequestBody DepositoRequestDTO depositoRequestDto){
        transacaoPJService.depositar(depositoRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/saque")
    public ResponseEntity sacar(@RequestBody SaqueRequestDTO saqueRequestDTO){
        transacaoPJService.sacar(saqueRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/transferencia")
    public ResponseEntity transferir(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO){
        transacaoPJService.transferir(transferenciaRequestDTO);
        return ResponseEntity.ok().build();
    }
}
