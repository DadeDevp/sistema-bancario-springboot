package br.caixa.sistemabancario.entity.prof.impl;



import br.caixa.sistemabancario.entity.ClientePF;
import br.caixa.sistemabancario.entity.Conta;
import br.caixa.sistemabancario.entity.ContaInvestimento;
import br.caixa.sistemabancario.entity.prof.interfaces.OperacoesBancarias;

import java.math.BigDecimal;

public class OperacoesContaClientePF implements OperacoesBancarias<ClientePF> {

    private static final BigDecimal RENDIMENTO_INVESTIMENTO = BigDecimal.valueOf(1.01);

    @Override
    public void sacar(ClientePF cliente, Long numeroConta, BigDecimal valor) {
        Conta conta = OperacoesBancarias.super.getContaCliente(cliente, numeroConta);
        OperacoesBancarias.super.verificarSaldo(conta, valor);
        conta.setSaldo(conta.getSaldo().subtract(valor));
    }

    @Override
    public void investir(ClientePF cliente, BigDecimal valor) {
        Conta conta = OperacoesBancarias.super.getContaInvestimentoCliente(cliente);
        if (conta == null) {
            conta = new ContaInvestimento(cliente);
            cliente.getContas().add(conta);
        }
        valor = valor.multiply(RENDIMENTO_INVESTIMENTO);
        conta.setSaldo(conta.getSaldo().add(valor));
    }

}
