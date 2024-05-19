package br.caixa.sistemabancario.dto.Conta;

import br.caixa.sistemabancario.entity.enums.TipoContaEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class ContaResponseDTO {
    private String cliente;
    private Long numero;
    private TipoContaEnum tipoConta;
    private BigDecimal saldo;
    private LocalDate dataCriacao;
}
