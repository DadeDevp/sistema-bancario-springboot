package br.caixa.sistemabancario.dto.Transacao;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaRequestDTO {
    @NotNull
    private Long numeroContaOrigem;
    @NotNull
    private Long numeroContadestino;
    @NotNull
    private BigDecimal valor;
}
