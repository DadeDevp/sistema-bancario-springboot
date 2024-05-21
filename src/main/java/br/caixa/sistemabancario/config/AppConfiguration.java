package br.caixa.sistemabancario.config;

import br.caixa.sistemabancario.dto.Cliente.ClientePJRequestDTO;
import br.caixa.sistemabancario.dto.Conta.ContaResponseDTO;
import br.caixa.sistemabancario.dto.Cliente.ClientePFRequestDTO;
import br.caixa.sistemabancario.entity.*;
import br.caixa.sistemabancario.entity.enums.RoleEnum;
import br.caixa.sistemabancario.repository.AgenteRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.DiscriminatorValue;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class AppConfiguration {

    private final AgenteRepository agenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AppConfiguration(AgenteRepository agenteRepository, PasswordEncoder passwordEncoder) {
        this.agenteRepository = agenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //Configure Mapper ClientePFRequestDTO
        modelMapper.typeMap(ClientePFRequestDTO.class, ClientePF.class)
                .addMapping(ClientePFRequestDTO::getCpf, ClientePF::setId);

        modelMapper.typeMap(ClientePJRequestDTO.class, ClientePJ.class)
                .addMapping(ClientePJRequestDTO::getCnpj, ClientePJ::setId);


        return modelMapper;
    }

    @PostConstruct
    public void criarAgentes() {
        Agente agenteOperator = new Agente(null, "Joao", "joao@gmail.com", passwordEncoder.encode("12345"));
        agenteOperator.getRoles().addAll(Arrays.asList(RoleEnum.ROLE_OPERATOR, RoleEnum.ROLE_VIEWER));

        Agente agenteViewer = new Agente(null, "Maria", "maria@gmail.com", passwordEncoder.encode("6789"));
        agenteViewer.getRoles().add(RoleEnum.ROLE_VIEWER);


        agenteRepository.saveAll(Arrays.asList(agenteOperator, agenteViewer));

    }
}
