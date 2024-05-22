package br.caixa.sistemabancario.dto.Transacao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaRequestDTO {
    @NotNull(message = "O numero da conta origem é obrigatório")
    private Long numeroContaOrigem;
    @NotNull
    private Long numeroContadestino;
    @NotNull(message = "O numero da conta destino é obrigatório")
    @Min(value = 1, message = "O valor tem que maior que 1")
    private BigDecimal valor;
}
