package br.caixa.sistemabancario.service;

import br.caixa.sistemabancario.dto.Transacao.DepositoRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.InvestimentoPFRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.SaqueRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.TransferenciaRequestDTO;
import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.Conta;
import br.caixa.sistemabancario.entity.ContaInvestimento;
import br.caixa.sistemabancario.exceptions.ValidacaoException;
import br.caixa.sistemabancario.repository.ClienteRepository;
import br.caixa.sistemabancario.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Service
public class TransacaoPFService {

    private static final BigDecimal RENDIMENTO_INVESTIMENTO = BigDecimal.valueOf(1.01);
    private final ModelMapper modelMapper;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public TransacaoPFService(ModelMapper modelMapper, ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.modelMapper = modelMapper;
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    
    public void depositar(DepositoRequestDTO depositoRequestDto) {
        Conta conta = findContaById(depositoRequestDto.getNumeroConta());
        conta.setSaldo(conta.getSaldo().add(depositoRequestDto.getValor()));
        contaRepository.save(conta);
    }

    
    public void sacar(SaqueRequestDTO saqueRequestDTO) {
        Conta conta = findContaById(saqueRequestDTO.getNumeroConta());

        verificarSaldo(conta.getSaldo(), saqueRequestDTO.getValor());

        conta.setSaldo(conta.getSaldo().subtract(saqueRequestDTO.getValor()));
        contaRepository.save(conta);
    }

    public void transferir(TransferenciaRequestDTO transferenciaRequestDTO) {

        //Pesquisa a conta origem e seu saldo
        Conta contaOrigem = findContaById(transferenciaRequestDTO.getNumeroContaOrigem());
        verificarSaldo(contaOrigem.getSaldo(), transferenciaRequestDTO.getValor());

        //Pesquisa conta destino
        Conta contaDestino = findContaById(transferenciaRequestDTO.getNumeroContadestino());

        //Debita conta origem
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(transferenciaRequestDTO.getValor()));

        //Credita conta destino
        contaDestino.setSaldo(contaDestino.getSaldo().add(transferenciaRequestDTO.getValor()));

        //Persistir no banco
        contaRepository.saveAll(Arrays.asList(contaOrigem, contaDestino));

    }

    public void investir(InvestimentoPFRequestDTO investimentoPFRequestDTO) {
        Cliente cliente = clienteRepository.findById(investimentoPFRequestDTO.getCpf()).orElseThrow(() -> new ValidacaoException("Cliente não encontrado", HttpStatus.BAD_REQUEST));
        Conta confInvestimento = cliente.getContas().stream().filter(c -> c instanceof ContaInvestimento).findFirst().orElse(criarContaInvestiment(cliente));

        BigDecimal valor = investimentoPFRequestDTO.getValor().multiply(RENDIMENTO_INVESTIMENTO);
        confInvestimento.setSaldo(confInvestimento.getSaldo().add(valor));

        contaRepository.save(confInvestimento);
    }

    private Conta findContaById(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new ValidacaoException("Conta de numero " + id + " nao encontrada", HttpStatus.NOT_FOUND));
    }

    private void verificarSaldo(BigDecimal valorSaldoAtual, BigDecimal valorDebito) {
        if (valorDebito.compareTo(valorSaldoAtual) > 0) {
            throw new ValidacaoException("Saldo insuficiente para efetuar esta transacao", HttpStatus.BAD_REQUEST);
        }
    }

    private Conta criarContaInvestiment(Cliente cliente) {
        ContaInvestimento contaInvestimento = new ContaInvestimento(cliente);
        contaInvestimento.setSaldo(BigDecimal.ZERO);
        contaInvestimento.setDataCriacao(LocalDate.now());

        return contaInvestimento;
    }
}
