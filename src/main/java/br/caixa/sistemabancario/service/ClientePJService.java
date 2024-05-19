package br.caixa.sistemabancario.service;

import br.caixa.sistemabancario.dto.Cliente.ClientePJRequestDTO;
import br.caixa.sistemabancario.dto.Cliente.ClientePJResponseDTO;
import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.ClientePJ;
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
public class ClientePJService {

    final ClienteRepository clienteRepository;
    final ModelMapper modelMapper;


    public ClientePJService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ClientePJResponseDTO inserirClientePJ(ClientePJRequestDTO ClientePJRequestDTO) {
        //Verificar se existe cliente com o cnpj já informado
        verificarCnpjCliente(ClientePJRequestDTO);

        //Conversao DTO para Entidade
        ClientePJ clientePJ = modelMapper.map(ClientePJRequestDTO, ClientePJ.class);
        clientePJ.setStatus(StatusClienteEnum.ATIVO);
        clientePJ.setDataCadastro(LocalDate.now());

        //Regra de negócio -> Criar conta corrente ao criar um cliente
        ContaCorrente contaCorrente = new ContaCorrente(clientePJ);
        contaCorrente.setSaldo(BigDecimal.ZERO);
        contaCorrente.setDataCriacao(LocalDate.now());

        clientePJ.getContas().add(contaCorrente);
        clientePJ = clienteRepository.save(clientePJ);

        ClientePJResponseDTO ClientePJResponseDTO = modelMapper.map(clientePJ, ClientePJResponseDTO.class);
        return ClientePJResponseDTO;
    }

    @Transactional
    public ClientePJResponseDTO atualizarclientePJ(String id, ClientePJRequestDTO ClientePJRequestDTO) {
        ClientePJRequestDTO.setCnpj(id);
        Cliente cliente = clienteRepository.findById(id)
                .map(clientePJ -> {
                    clientePJ.setNome(ClientePJRequestDTO.getNome());
                    clientePJ.setStatus(ClientePJRequestDTO.getStatus());
                    return clienteRepository.save(clientePJ);
                })
                .orElseThrow(() -> new ValidacaoException("clientePJ nao encontrado!", HttpStatus.NOT_FOUND));

        return modelMapper.map(cliente, ClientePJResponseDTO.class);
    }

    public void excluir(String id) {
        clienteRepository.deleteById(id);
    }

    public ClientePJResponseDTO buscarclientePJPorId(String id) {
        return clienteRepository.findById(id)
                .map(cliente -> modelMapper.map(cliente, ClientePJResponseDTO.class))
                .orElseThrow(() -> new ValidacaoException("clientePJ nao encontrado!", HttpStatus.NOT_FOUND));
    }

    public List<ClientePJResponseDTO> buscarTodosClientesPJ() {
        return clienteRepository.findAllPJ()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClientePJResponseDTO.class))
                .toList();
    }

    private void verificarCnpjCliente(ClientePJRequestDTO ClientePJRequestDTO) {
        Optional<Cliente> obj = clienteRepository.findById(ClientePJRequestDTO.getCnpj());
        if (obj.isPresent() && obj.get().getId().equals(ClientePJRequestDTO.getCnpj())) {
            throw new ValidacaoException("Já existe cliente cadastrado para este cnpj", HttpStatus.BAD_REQUEST);
        }
    }
}

