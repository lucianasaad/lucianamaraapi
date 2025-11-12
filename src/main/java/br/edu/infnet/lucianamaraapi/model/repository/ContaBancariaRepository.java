package br.edu.infnet.lucianamaraapi.model.repository;

import br.edu.infnet.lucianamaraapi.model.domain.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer> {
    Optional<ContaBancaria> findByBancoIdAndAgenciaAndNumeroConta(Integer bancoId, String agencia, String numeroConta);
}
