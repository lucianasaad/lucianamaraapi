package br.edu.infnet.lucianamaraapi.model.repository;

import br.edu.infnet.lucianamaraapi.model.domain.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BancoRepository extends JpaRepository<Banco, Integer> {
    Optional<Banco> findByNomeIgnoreCase(String nome);
    Optional<Banco> findByCodigo(String codigo);
}
