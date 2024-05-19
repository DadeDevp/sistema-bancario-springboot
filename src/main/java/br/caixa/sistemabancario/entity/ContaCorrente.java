package br.caixa.sistemabancario.entity;

import br.caixa.sistemabancario.entity.enums.TipoContaEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("Conta_Corrente")
public class ContaCorrente extends Conta {
    public ContaCorrente(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void setTipoContaEnum() {
       this.tipoConta = TipoContaEnum.ContaCorrente;
    }
}
