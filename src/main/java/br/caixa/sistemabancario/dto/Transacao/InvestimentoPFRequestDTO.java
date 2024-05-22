package br.caixa.sistemabancario.dto.Transacao;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

@Setter
@Getter
public class InvestimentoPFRequestDTO {
    @CPF
    private String cpf;
    @NotNull(message = "O valor é obrigatório")
    private BigDecimal valor;
}
