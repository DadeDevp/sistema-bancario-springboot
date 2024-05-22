package br.caixa.sistemabancario.config.security;

import br.caixa.sistemabancario.entity.Agente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipalDetails implements UserDetails {

    final Agente agente;

    public UserPrincipalDetails(Agente agente) {
        this.agente = agente;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return agente.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name().toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return agente.getSenha();
    }

    @Override
    public String getUsername() {
        return agente.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return agente.getEmail();
    }
}
