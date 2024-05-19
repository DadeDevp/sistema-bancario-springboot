package br.caixa.sistemabancario.entity.prof.interfaces;


import br.caixa.sistemabancario.entity.Cliente;

import java.math.BigDecimal;

public interface Investimento<T extends Cliente> {

    void investir(T cliente, BigDecimal valor);

}
