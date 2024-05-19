package br.caixa.sistemabancario.service;

import br.caixa.sistemabancario.dto.Transacao.DepositoRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.SaqueRequestDTO;
import br.caixa.sistemabancario.dto.Transacao.TransferenciaRequestDTO;
import br.caixa.sistemabancario.entity.Conta;
import br.caixa.sistemabancario.exceptions.ValidacaoException;
import br.caixa.sistemabancario.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class TransacaoPFService {

    private final ModelMapper modelMapper;
    private final ContaRepository contaRepository;

    public TransacaoPFService(ModelMapper modelMapper, ContaRepository contaRepository) {
        this.modelMapper = modelMapper;
        this.contaRepository = contaRepository;
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
        contaRepository.saveAll(Arrays.asList(contaOrigem,contaDestino));

    }

    private Conta findContaById(Long id ){
        return contaRepository.findById(id).orElseThrow(() -> new ValidacaoException("Conta de numero " + id + " nao encontrada" , HttpStatus.NOT_FOUND));
    }

    private void verificarSaldo(BigDecimal valorSaldoAtual, BigDecimal valorDebito){
        if (valorDebito.compareTo(valorSaldoAtual) > 0) {
            throw new ValidacaoException("Saldo insuficiente para efetuar esta transacao",HttpStatus.BAD_REQUEST);
        }
    }
}
