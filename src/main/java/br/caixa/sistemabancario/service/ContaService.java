package br.caixa.sistemabancario.service;

import br.caixa.sistemabancario.dto.Conta.ContaResponseDTO;
import br.caixa.sistemabancario.entity.*;
import br.caixa.sistemabancario.exceptions.ValidacaoException;
import br.caixa.sistemabancario.repository.ClienteRepository;
import br.caixa.sistemabancario.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ContaService {

    private final ModelMapper modelMapper;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ModelMapper modelMapper, ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.modelMapper = modelMapper;
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public ContaResponseDTO criarContaCorrente(String id){
        Cliente cliente = findClienteById(id);

        ContaCorrente contaCorrente = new ContaCorrente(cliente);
        contaCorrente.setSaldo(BigDecimal.ZERO);
        contaCorrente.setDataCriacao(LocalDate.now());
        contaRepository.save(contaCorrente);

        return modelMapper.map(contaCorrente, ContaResponseDTO.class);
    }

    public ContaResponseDTO criarContaPoupanca(String id){
        Cliente cliente = findClienteById(id);

        //Regra de negócio a conta poupanca só pode ser criada por ClientePF
        if (!(cliente instanceof ClientePF)) {
            throw new ValidacaoException("Cliente nao encontrado", HttpStatus.BAD_REQUEST);
        }
        ContaPoupanca contaPoupanca = new ContaPoupanca((ClientePF) cliente);
        contaPoupanca.setSaldo(BigDecimal.ZERO);
        contaPoupanca.setDataCriacao(LocalDate.now());

        contaRepository.save(contaPoupanca);
        return modelMapper.map(contaPoupanca, ContaResponseDTO.class);
    }

    public ContaResponseDTO criarContaInvestimento(String id){
        Cliente cliente = findClienteById(id);
        ContaInvestimento contaInvestimento = new ContaInvestimento(cliente);
        contaInvestimento.setSaldo(BigDecimal.ZERO);
        contaInvestimento.setDataCriacao(LocalDate.now());

        contaRepository.save(contaInvestimento);
        return modelMapper.map(contaInvestimento, ContaResponseDTO.class);
    }

    private Cliente findClienteById(String id){
        return clienteRepository.findById(id).orElseThrow(() -> new ValidacaoException("Cliente nao encontrado",HttpStatus.NOT_FOUND));
    }

    public ContaResponseDTO buscarContaPorNumero(Integer numeroConta) {
        Conta conta = contaRepository.findById(Long.valueOf(numeroConta)).orElseThrow(() -> new ValidacaoException("Conta nao encontrada", HttpStatus.NOT_FOUND));
        return modelMapper.map(conta, ContaResponseDTO.class);
    }
}
