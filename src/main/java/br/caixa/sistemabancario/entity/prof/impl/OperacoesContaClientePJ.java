package br.caixa.sistemabancario.entity.prof.impl;



import br.caixa.sistemabancario.entity.ClientePJ;
import br.caixa.sistemabancario.entity.Conta;
import br.caixa.sistemabancario.entity.ContaInvestimento;
import br.caixa.sistemabancario.entity.prof.interfaces.OperacoesBancarias;

import java.math.BigDecimal;

public class OperacoesContaClientePJ implements OperacoesBancarias<ClientePJ> {

    private static final BigDecimal RENDIMENTO_INVESTIMENTO = BigDecimal.valueOf(1.02);
    private static final BigDecimal TAXA_RETIRADA = BigDecimal.valueOf(1.005);

    @Override
    public void investir(ClientePJ cliente, BigDecimal valor) {
        Conta conta = OperacoesBancarias.super.getContaInvestimentoCliente(cliente);
        if (conta == null) {
            conta = new ContaInvestimento(cliente);
            cliente.getContas().add(conta);
        }
        valor = valor.multiply(RENDIMENTO_INVESTIMENTO);
        conta.setSaldo(conta.getSaldo().add(valor));
    }

    @Override
    public void sacar(ClientePJ cliente, Long numeroConta, BigDecimal valor) {
        Conta conta = OperacoesBancarias.super.getContaCliente(cliente, numeroConta);
        valor = valor.multiply(TAXA_RETIRADA);
        OperacoesBancarias.super.verificarSaldo(conta, valor);
        conta.setSaldo(conta.getSaldo().subtract(valor));
    }

}
