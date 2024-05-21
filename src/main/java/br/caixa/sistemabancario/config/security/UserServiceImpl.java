package br.caixa.sistemabancario.config.security;


import br.caixa.sistemabancario.entity.Agente;
import br.caixa.sistemabancario.repository.AgenteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    final AgenteRepository agenteRepository;

    public UserServiceImpl(AgenteRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Agente agente = agenteRepository.findByEmail(email);

        return new UserPrincipalDetails(agente);
    }

}
