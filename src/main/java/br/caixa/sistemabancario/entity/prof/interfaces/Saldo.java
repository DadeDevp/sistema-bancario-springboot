package br.caixa.sistemabancario.entity.prof.interfaces;


import br.caixa.sistemabancario.entity.Cliente;

import java.math.BigDecimal;

public interface Saldo<T extends Cliente> {

    BigDecimal consultarSaldo(T cliente, Long numeroConta);

}
