package br.caixa.sistemabancario.controller;

import br.caixa.sistemabancario.dto.Transacao.DepositoRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.SaqueRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.TransferenciaRequestDTO;
import br.caixa.sistemabancario.service.TransacaoPFService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes/pf")
public class TransacaoPFController {

    final private TransacaoPFService transacaoPFService;

    public TransacaoPFController(TransacaoPFService transacaoPFService) {
        this.transacaoPFService = transacaoPFService;
    }

    @PutMapping("/deposito")
    public ResponseEntity depositar(@RequestBody DepositoRequestDTO depositoRequestDto){
        transacaoPFService.depositar(depositoRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/saque")
    public ResponseEntity sacar(@RequestBody SaqueRequestDTO saqueRequestDTO){
        transacaoPFService.sacar(saqueRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/transferencia")
    public ResponseEntity transferir(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO){
        transacaoPFService.transferir(transferenciaRequestDTO);
        return ResponseEntity.ok().build();
    }


}
