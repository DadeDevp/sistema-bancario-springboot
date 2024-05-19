package br.caixa.sistemabancario.dto.Transacao;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaRequestDTO {
    private Long numeroContaOrigem;
    private Long numeroContadestino;
    private BigDecimal valor;
}
