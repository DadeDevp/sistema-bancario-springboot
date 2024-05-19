package br.caixa.sistemabancario.repository;

import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta,Long> {
}
