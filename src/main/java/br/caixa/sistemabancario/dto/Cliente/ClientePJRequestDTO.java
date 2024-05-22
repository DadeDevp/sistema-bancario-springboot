package br.caixa.sistemabancario.dto.Cliente;

import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class ClientePJRequestDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @CNPJ
    private String cnpj;
    @NotBlank(message = "A razao social é obrigatória")
    private String razaoSocial;

    private StatusClienteEnum status;
}

