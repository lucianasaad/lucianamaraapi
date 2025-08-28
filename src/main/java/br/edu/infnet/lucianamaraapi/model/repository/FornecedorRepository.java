package br.edu.infnet.lucianamaraapi.model.repository;

import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Optional<Fornecedor> findByDocumento(String documento);

}