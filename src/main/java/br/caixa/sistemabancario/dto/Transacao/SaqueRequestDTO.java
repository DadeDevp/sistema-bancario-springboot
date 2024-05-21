package br.caixa.sistemabancario.dto.Transacao;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SaqueRequestDTO {
    @NotNull
    private Long numeroConta;
    @NotNull
    private BigDecimal valor;
}
