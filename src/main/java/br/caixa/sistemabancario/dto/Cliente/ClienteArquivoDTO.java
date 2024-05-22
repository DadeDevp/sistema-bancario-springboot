package br.caixa.sistemabancario.dto.Cliente;

import br.caixa.sistemabancario.entity.ClientePF;
import br.caixa.sistemabancario.entity.ClientePJ;
import br.caixa.sistemabancario.entity.enums.TipoClienteEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ClienteArquivoDTO {
    private String nome;
    private String documento;
    private String tipo;
    private Long numeroConta;
    private BigDecimal saldoConta;

    public ClienteArquivoDTO(ClientePF clientePF) {
        this.nome = clientePF.getNome();
        this.documento = clientePF.getCpf();
        this.tipo = TipoClienteEnum.PF.toString();
        this.numeroConta = clientePF.getContas().get(0).getNumero();
        this.saldoConta = clientePF.getContas().get(0).getSaldo();
    }

    public ClienteArquivoDTO(ClientePJ clientePJ) {
        this.nome = clientePJ.getNome();
        this.documento = clientePJ.getCnpj();
        this.tipo = TipoClienteEnum.PJ.toString();
        this.numeroConta = clientePJ.getContas().get(0).getNumero();
        this.saldoConta = clientePJ.getContas().get(0).getSaldo();
    }
}
