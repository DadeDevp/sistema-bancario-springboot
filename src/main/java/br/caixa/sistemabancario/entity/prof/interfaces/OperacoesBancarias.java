package br.caixa.sistemabancario.entity.prof.interfaces;



import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.Conta;
import br.caixa.sistemabancario.entity.ContaInvestimento;
import br.caixa.sistemabancario.entity.prof.factory.OperacoesBancariasFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface OperacoesBancarias<T extends Cliente> extends
        Deposito<T>, Saldo<T>, Saque<T>, Transferencia<T>, Investimento<T> {

    default Conta getContaCliente(T cliente, Long numeroConta) {
        for (Conta conta : cliente.getContas()) {
            if (conta.getNumero().equals(numeroConta)) {
                return conta;
            }
        }
//        throw new ContaNaoEncontradaException();
        throw new RuntimeException("Erro");
    }

    default Conta getContaInvestimentoCliente(T cliente) {
        for (Conta conta : cliente.getContas()) {
            if (conta instanceof ContaInvestimento) {
                return conta;
            }
        }
        return null;
    }

    @Override
    default BigDecimal consultarSaldo(T cliente, Long numeroConta) {
        Conta conta = getContaCliente(cliente, numeroConta);
        return conta.getSaldo().setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    default void transferir(T cliente, Long numeroContaOrigem, Conta destino, BigDecimal valor) {
        this.sacar(cliente, numeroContaOrigem, valor);
        OperacoesBancariasFactory.getInstance().get(destino.getCliente())
                .depositar(destino.getCliente(), destino.getNumero(), valor);
    }

    default void depositar(T cliente, Long numeroConta, BigDecimal valor) {
        Conta conta = this.getContaCliente(cliente, numeroConta);
        conta.setSaldo(conta.getSaldo().add(valor));
    }

}
