package br.caixa.sistemabancario.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
public class ClientePF extends Cliente {

    private String cpf;
    private LocalDate dataNascimento;

}


