package br.caixa.sistemabancario.dto.Conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContaRequestDTO {
    @NotNull
    @NotBlank
    private String documento;
}
