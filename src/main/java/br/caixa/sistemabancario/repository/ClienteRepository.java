package br.caixa.sistemabancario.repository;

import br.caixa.sistemabancario.entity.Cliente;
import br.caixa.sistemabancario.entity.ClientePF;
import br.caixa.sistemabancario.entity.ClientePJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,String> {

    @Query("select c from ClientePF c")
    List<ClientePF> findAllPF();

    @Query("select c from ClientePJ c")
    List<ClientePJ> findAllPJ();
}
