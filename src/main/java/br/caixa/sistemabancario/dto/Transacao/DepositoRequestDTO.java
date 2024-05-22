package br.caixa.sistemabancario.dto.Transacao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DepositoRequestDTO {
    @NotNull(message = "O número da conta é obrigatório")
    private Long numeroConta;
    @NotNull(message = "O valor é obrigatório")
    @Min(value = 1)
    private BigDecimal valor;
}
