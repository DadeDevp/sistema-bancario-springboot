package br.caixa.sistemabancario.entity;

import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Cliente {

    @Id
    private String id;
    private String nome;
    private LocalDate dataCadastro = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private StatusClienteEnum status = StatusClienteEnum.ATIVO;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Conta> contas = new ArrayList<>();

}
