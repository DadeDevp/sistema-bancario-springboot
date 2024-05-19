package br.caixa.sistemabancario.dto.Cliente;

import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
public class ClientePFRequestDTO {

    @NotBlank
    private String nome;
    @CPF
    private String cpf;
    @NotNull
    @Past
    private LocalDate dataNascimento;

    private StatusClienteEnum status;
}

