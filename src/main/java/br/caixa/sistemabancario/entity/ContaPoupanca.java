package br.caixa.sistemabancario.entity;

import br.caixa.sistemabancario.entity.enums.TipoContaEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("Conta_Poupanca")
public class ContaPoupanca extends Conta {

    //apenas PF pode ter conta poupança
    public ContaPoupanca(ClientePF cliente) {
        super(cliente);
    }

    @Override
    public void setTipoContaEnum() {
        this.tipoConta = TipoContaEnum.ContaPoupanca;
    }
}
