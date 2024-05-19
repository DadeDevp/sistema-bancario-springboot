package br.caixa.sistemabancario.dto.Transacao;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DepositoRequestDTO {
    private Long numeroConta;
    private BigDecimal valor;
}
