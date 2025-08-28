package br.edu.infnet.lucianamaraapi.model.repository;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByDocumento(String documento);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}