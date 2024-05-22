package br.caixa.sistemabancario.service;

import br.caixa.sistemabancario.dto.Cliente.ClienteArquivoDTO;
import br.caixa.sistemabancario.entity.ClientePF;
import br.caixa.sistemabancario.entity.ClientePJ;
import br.caixa.sistemabancario.entity.ContaCorrente;
import br.caixa.sistemabancario.entity.enums.StatusClienteEnum;
import br.caixa.sistemabancario.entity.enums.TipoClienteEnum;
import br.caixa.sistemabancario.exceptions.ValidacaoException;
import br.caixa.sistemabancario.repository.ClienteRepository;
import br.caixa.sistemabancario.service.utils.AjusteData;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArquivoService {

    private final BigDecimal VALOR_CREDITO = new BigDecimal("50");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyy_HH-mm-ss");

    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;

    public ArquivoService(ModelMapper modelMapper, ClienteRepository clienteRepository) {
        this.modelMapper = modelMapper;
        this.clienteRepository = clienteRepository;
    }


    public ResponseEntity<byte[]> processarArquivoCsv(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ValidacaoException("O arquivo está vazio", HttpStatus.BAD_REQUEST);
        }


        try {
            Path destination = armazenarArquivoDeChegada(file);
            Path arquivoCSVProcessado = criarContasDoArquivo(destination);
            byte[] fileContent = Files.readAllBytes(arquivoCSVProcessado);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + arquivoCSVProcessado.getFileName().toString());
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);

        } catch (IOException e) {
            throw new ValidacaoException("Erro no processamento do arquivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Path armazenarArquivoDeChegada(MultipartFile file) throws IOException {
        // Obtenha o diretório raiz do projeto
        Path rootPath = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();

        // Nome da pasta dentro da raiz do projeto
        String folderName = "ArquivoInput";
        Path uploadDirectory = rootPath.resolve(folderName).normalize();

        //  o diretório se não existir
        if (Files.notExists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }

        String fileName = file.getOriginalFilename() + LocalDateTime.now().format(FORMATTER);

        Path destination = uploadDirectory.resolve(fileName).normalize();

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return destination;
    }

    public Path criarContasDoArquivo(Path pathOrigem) throws IOException {

        //Obter as linhas do arquivo
        List<String> linhasArquivos = Files.lines(pathOrigem).toList();
        List<String> clientesProcessados = linhasArquivos.stream()
                .skip(1)
                .map(linhas -> linhas.split(","))
                .filter(clientes -> !("2".equals(clientes[3].trim()) && verificaMenorDeDezoito(clientes[1])))
                .map(cliente -> criarConta(cliente))
                .map(ClienteArquivoDTO -> prepararParaArquivo(ClienteArquivoDTO))
                .collect(Collectors.toList());

        //Define o path de destino
        Path destino = Path.of("ArquivoOutput/");

        //Verifica se o arquivo existe, se nao existe cria o mesmo
        if (Files.notExists(destino)) {
            Files.createDirectories(destino);
        }

        Path arquivo = destino.resolve("Clientes_Processados" + LocalDateTime.now().format(FORMATTER) + ".csv");

        //Adiciona o cabeçalho na lista
        String cabecalho = "nome do cliente;documento;PF/PJ;número da conta;saldo em conta";
        clientesProcessados.add(0, cabecalho);

        //Insere os dados no arquivo
        Files.write(arquivo, clientesProcessados);

        return arquivo;

    }

    private ClienteArquivoDTO criarConta(String[] cliente) {
        //Capturar os atributos do cliente
        TipoClienteEnum tipoCliente = cliente[3].equals("2") ? TipoClienteEnum.PF : TipoClienteEnum.PJ;
        String nomeCliente = cliente[0];
        String documento = cliente[2];

        //Se houver um cliente no documento que já existe
        if (clienteRepository.findById(documento).isPresent()) {
            ClienteArquivoDTO clienteArquivoDTO = new ClienteArquivoDTO();
            clienteArquivoDTO.setNome("Cliente do documento " + documento + " já cadastrado no banco");
            return clienteArquivoDTO;
        }

        //Criar conta corrente
        ContaCorrente conta = new ContaCorrente();
        conta.setTipoContaEnum();
        conta.setDataCriacao(LocalDate.now());
        conta.setSaldo(VALOR_CREDITO);

        if (tipoCliente.equals(TipoClienteEnum.PF)) {
            ClientePF clientePF = new ClientePF();
            clientePF.setId(documento);
            clientePF.setNome(nomeCliente);
            clientePF.setDataNascimento(AjusteData.convertTextToLocalDate(cliente[1]));
            clientePF.setStatus(StatusClienteEnum.ATIVO);
            clientePF.setDataCadastro(LocalDate.now());
            clientePF.setCpf(documento);

            conta.setCliente(clientePF);
            clientePF.getContas().add(conta);

            return new ClienteArquivoDTO(clienteRepository.save(clientePF));

        }
        ClientePJ clientePJ = new ClientePJ();
        clientePJ.setId(documento);
        clientePJ.setNome(nomeCliente);
        clientePJ.setStatus(StatusClienteEnum.INATIVO);
        clientePJ.setDataCadastro(LocalDate.now());
        clientePJ.setCnpj(documento);

        conta.setCliente(clientePJ);
        clientePJ.getContas().add(conta);

        return new ClienteArquivoDTO(clienteRepository.save(clientePJ));
    }

    private String prepararParaArquivo(ClienteArquivoDTO clienteArquivoDTO) {
        return clienteArquivoDTO.getNome() + ";" +
                clienteArquivoDTO.getDocumento() + ";" +
                clienteArquivoDTO.getTipo() + ";" +
                clienteArquivoDTO.getNumeroConta() + ";" +
                clienteArquivoDTO.getSaldoConta();
    }

    private boolean verificaMenorDeDezoito(String data) {
        LocalDate date = AjusteData.convertTextToLocalDate(data);
        return ChronoUnit.YEARS.between(date, LocalDate.now()) < 18;
    }

}
