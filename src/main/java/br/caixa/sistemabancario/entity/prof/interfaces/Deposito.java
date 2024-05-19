package br.caixa.sistemabancario.entity.prof.interfaces;



import br.caixa.sistemabancario.entity.Cliente;

import java.math.BigDecimal;

public interface Deposito<T extends Cliente> {

    void depositar(T cliente, Long numeroConta, BigDecimal valor);

}
