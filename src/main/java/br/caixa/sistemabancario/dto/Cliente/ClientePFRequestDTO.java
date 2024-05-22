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

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @CPF
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;
    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento tem que anterior a data atual")
    private LocalDate dataNascimento;

    private StatusClienteEnum status;
}

