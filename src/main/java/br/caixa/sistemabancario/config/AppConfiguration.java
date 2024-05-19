package br.caixa.sistemabancario.config;

import br.caixa.sistemabancario.dto.Cliente.ClientePJRequestDTO;
import br.caixa.sistemabancario.dto.Conta.ContaResponseDTO;
import br.caixa.sistemabancario.dto.Cliente.ClientePFRequestDTO;
import br.caixa.sistemabancario.entity.*;
import jakarta.persistence.DiscriminatorValue;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        //Configure Mapper ClientePFRequestDTO
        modelMapper.typeMap(ClientePFRequestDTO.class, ClientePF.class)
                .addMapping(ClientePFRequestDTO::getCpf, ClientePF::setId);

        modelMapper.typeMap(ClientePJRequestDTO.class, ClientePJ.class)
                .addMapping(ClientePJRequestDTO::getCnpj, ClientePJ::setId);

        //Configure Mapper ContaResponseDto

//        PropertyMap<Conta, ContaResponseDTO> contaMap = new PropertyMap<Conta, ContaResponseDTO>() {
//            protected void configure() {
//                map().setCliente(source.getCliente().getNome());
//                map().setNumero(source.getNumero());
//                map().setSaldo(source.getSaldo());
//                map().setDataCriacao(source.getDataCriacao());
//                map().setTipoConta(source.getClass().getAnnotation(DiscriminatorValue.class).value());
//            }
//        };


//        Converter<Conta, String> toTipoConta = new Converter<Conta, String>() {
//            public String convert(MappingContext<Conta, String> context) {
//                Conta source = context.getSource();
//                if (source instanceof ContaCorrente) {
//                    return ContaCorrente.class.getAnnotation(DiscriminatorValue.class).value();
//                }
//                return null;
//            }
//        };

//        modelMapper.createTypeMap(Conta.class, ContaResponseDTO.class)
//                .addMappings(mapper -> mapper.using(toTipoConta).map(Conta::getClass, ContaResponseDTO::setTipoConta));
//
//        modelMapper.typeMap(Conta.class, ContaResponseDTO.class)
//                .addMapping(conta -> conta.getTipoConta(), ContaResponseDTO::setTipoConta);

//        modelMapper.typeMap(ContaCorrente.class, ContaResponseDTO.class)
//                .addMapping(conta -> conta.getCliente().getNome(), ContaResponseDTO::setCliente);
//        modelMapper.typeMap(ContaPoupanca.class, ContaResponseDTO.class)
//                .addMapping(conta -> conta.getCliente().getNome(), ContaResponseDTO::setCliente);
//        modelMapper.typeMap(ContaInvestimento.class, ContaResponseDTO.class)
//                .addMapping(conta -> conta.getCliente().getNome(), ContaResponseDTO::setCliente);


        return modelMapper;
    }
}
