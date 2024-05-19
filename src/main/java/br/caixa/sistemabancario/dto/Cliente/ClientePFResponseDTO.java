package br.caixa.sistemabancario.dto.Cliente;

import br.caixa.sistemabancario.dto.Conta.ContaResponseDTO;
import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClientePFResponseDTO {
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private LocalDate dataCadastro;
    private StatusClienteEnum status;
    private List<ContaResponseDTO> contas;

}

