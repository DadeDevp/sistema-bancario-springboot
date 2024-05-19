package br.caixa.sistemabancario.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class ClientePJ extends Cliente {

    private String cnpj;
    private String razaoSocial;
}
