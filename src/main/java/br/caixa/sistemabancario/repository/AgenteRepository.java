package br.caixa.sistemabancario.repository;

import br.caixa.sistemabancario.entity.Agente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenteRepository extends JpaRepository<Agente,Long> {
    Agente findByEmail(String email);
}
