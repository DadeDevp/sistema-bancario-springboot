package br.caixa.sistemabancario.entity;

import br.caixa.sistemabancario.entity.enums.TipoContaEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipos_conta", discriminatorType = DiscriminatorType.STRING)
@Entity
public abstract class Conta {

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    protected TipoContaEnum tipoConta;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long numero;
    private BigDecimal saldo;
    private LocalDate dataCriacao;

    @ManyToOne /*(cascade=CascadeType.ALL)*/
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    public Conta(Cliente cliente) {
        this.cliente = cliente;
        setTipoContaEnum();
    }
    public abstract void setTipoContaEnum();
}
