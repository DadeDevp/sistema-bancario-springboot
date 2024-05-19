package br.caixa.sistemabancario.entity.prof.interfaces;




import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.Conta;

import java.math.BigDecimal;

public interface Saque<T extends Cliente> {

    void sacar(T cliente, Long numeroConta, BigDecimal valor);

    default void verificarSaldo(Conta conta, BigDecimal valor) {
        if (valor.compareTo(conta.getSaldo()) > 0) {
//            throw new SaldoInsuficienteException();
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    default BigDecimal getTaxaSaque() {
        return BigDecimal.valueOf(1);
    }

}
