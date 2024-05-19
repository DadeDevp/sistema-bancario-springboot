package br.caixa.sistemabancario.dto.Cliente;

import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class ClientePJRequestDTO {
    @NotBlank
    private String nome;
    @CNPJ
    private String cnpj;
    @NotBlank
    private String razaoSocial;

    private StatusClienteEnum status;
}

