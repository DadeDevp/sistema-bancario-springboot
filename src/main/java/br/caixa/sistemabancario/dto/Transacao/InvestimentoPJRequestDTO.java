package br.caixa.sistemabancario.dto.Transacao;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

@Setter
@Getter
public class InvestimentoPJRequestDTO {
    @CNPJ
    private String cnpj;
    @NotNull
    private BigDecimal valor;
}
