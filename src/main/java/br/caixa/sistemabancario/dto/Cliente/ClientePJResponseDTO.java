package br.caixa.sistemabancario.dto.Cliente;

import br.caixa.sistemabancario.dto.Conta.ContaResponseDTO;
import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClientePJResponseDTO {
    @CNPJ
    private String cnpj;
    private String nome;
    private String razaoSocial;
    private LocalDate dataCadastro;
    private StatusClienteEnum status;
    private List<ContaResponseDTO> contas;

}

