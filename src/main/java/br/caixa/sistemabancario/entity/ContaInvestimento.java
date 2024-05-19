package br.caixa.sistemabancario.entity;

import br.caixa.sistemabancario.entity.enums.TipoContaEnum;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("Conta_Investimento")
public class ContaInvestimento extends Conta {

    public ContaInvestimento(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void setTipoContaEnum() {
        this.tipoConta = TipoContaEnum.ContaInvestimento;
    }

}
