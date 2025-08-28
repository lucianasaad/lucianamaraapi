package br.edu.infnet.lucianamaraapi.model.repository;

import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByDocumento(String documento);
}