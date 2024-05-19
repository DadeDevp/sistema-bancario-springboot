package br.caixa.sistemabancario.entity.prof.factory;


import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.ClientePF;
import br.caixa.sistemabancario.entity.ClientePJ;
import br.caixa.sistemabancario.entity.prof.impl.OperacoesContaClientePF;
import br.caixa.sistemabancario.entity.prof.impl.OperacoesContaClientePJ;
import br.caixa.sistemabancario.entity.prof.interfaces.OperacoesBancarias;

public final class OperacoesBancariasFactory {

    private final OperacoesBancarias<ClientePF> opeBancPF;
    private final OperacoesBancarias<ClientePJ> opeBancPJ;

    private static OperacoesBancariasFactory instance;

    private OperacoesBancariasFactory() {
        this.opeBancPF = new OperacoesContaClientePF();
        this.opeBancPJ = new OperacoesContaClientePJ();
    }

    public static OperacoesBancariasFactory getInstance() {
        if (instance == null) {
            instance = new OperacoesBancariasFactory();
        }
        return instance;
    }

    public OperacoesBancarias get(Cliente cliente) {
        if (cliente instanceof ClientePF) {
            return this.opeBancPF;
        } else if (cliente instanceof ClientePJ) {
            return this.opeBancPJ;
        } else {
//            throw new TipoClienteInvalidoException();
            throw new RuntimeException("Erro factory");
        }
    }

}
