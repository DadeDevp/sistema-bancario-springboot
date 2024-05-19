package br.caixa.sistemabancario.entity.prof.interfaces;



import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.Conta;

import java.math.BigDecimal;

public interface Transferencia<T extends Cliente> {

    void transferir(T cliente, Long numeroContaOrigem, Conta destino, BigDecimal valor);

}
