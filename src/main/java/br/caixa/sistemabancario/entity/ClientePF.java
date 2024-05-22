package br.caixa.sistemabancario.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;


@Data
@Entity
public class ClientePF extends Cliente {

    private String cpf;
    private LocalDate dataNascimento;


}


