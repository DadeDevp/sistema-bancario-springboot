package br.caixa.sistemabancario.service;

import br.caixa.sistemabancario.dto.Cliente.ClientePFRequestDTO;
import br.caixa.sistemabancario.dto.Cliente.ClientePFResponseDTO;
import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.ClientePF;
import br.caixa.sistemabancario.entity.ContaCorrente;
import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import br.caixa.sistemabancario.exceptions.ValidacaoException;
import br.caixa.sistemabancario.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientePFService {

    final ClienteRepository clienteRepository;
    final ModelMapper modelMapper;


    public ClientePFService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ClientePFResponseDTO inserirClientePF(ClientePFRequestDTO clientePFRequestDTO) {
        //Verificar se existe cliente com o cpf já informado
        verificarCpfCliente(clientePFRequestDTO);

        //Conversao DTO para Entidade
        ClientePF clientePF = modelMapper.map(clientePFRequestDTO, ClientePF.class);
        clientePF.setStatus(StatusClienteEnum.ATIVO);
        clientePF.setDataCadastro(LocalDate.now());

        //Regra de negócio -> Criar conta corrente ao criar um cliente
        ContaCorrente contaCorrente = new ContaCorrente(clientePF);
        contaCorrente.setSaldo(BigDecimal.ZERO);
        contaCorrente.setDataCriacao(LocalDate.now());

        clientePF.getContas().add(contaCorrente);
        clientePF = clienteRepository.save(clientePF);

        ClientePFResponseDTO clientePFResponseDTO = modelMapper.map(clientePF, ClientePFResponseDTO.class);
        return clientePFResponseDTO;
    }

    @Transactional
    public ClientePFResponseDTO atualizarClientePF(String id, ClientePFRequestDTO clientePFRequestDTO) {
        clientePFRequestDTO.setCpf(id);
        Cliente cliente = clienteRepository.findById(id)
                .map(clientePF -> {
                    clientePF.setNome(clientePFRequestDTO.getNome());
                    clientePF.setStatus(clientePFRequestDTO.getStatus());
                    return clienteRepository.save(clientePF);
                })
                .orElseThrow(() -> new ValidacaoException("ClientePF nao encontrado!", HttpStatus.NOT_FOUND));

        return modelMapper.map(cliente, ClientePFResponseDTO.class);
    }

    public void excluir(String id) {
        clienteRepository.deleteById(id);
    }

    public ClientePFResponseDTO buscarClientePFPorId(String id) {
        return clienteRepository.findById(id)
                .map(cliente -> modelMapper.map(cliente, ClientePFResponseDTO.class))
                .orElseThrow(() -> new ValidacaoException("ClientePF nao encontrado!", HttpStatus.NOT_FOUND));
    }

    public List<ClientePFResponseDTO> buscarTodosClientesPF() {
        return clienteRepository.findAllPF()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClientePFResponseDTO.class))
                .toList();
    }

    private void verificarCpfCliente(ClientePFRequestDTO clientePFRequestDTO) {
        Optional<Cliente> obj = clienteRepository.findById(clientePFRequestDTO.getCpf());
        if (obj.isPresent() && obj.get().getId().equals(clientePFRequestDTO.getCpf())) {
            throw new ValidacaoException("Já existe cliente cadastrado para este cpf", HttpStatus.BAD_REQUEST);
        }
    }
}

