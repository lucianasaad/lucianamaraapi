package br.edu.infnet.lucianamaraapi.model.repository;

import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinanceiroRepository extends JpaRepository<Financeiro, Integer> {
    // Buscar todos os financeiros com data de vencimento exata
    List<Financeiro> findByDataVencimento(LocalDate dataVencimento);

    // Buscar financeiros com data de vencimento antes de uma data
    List<Financeiro> findByDataVencimentoBefore(LocalDate data);

    // Buscar financeiros com data de vencimento depois de uma data
    List<Financeiro> findByDataVencimentoAfter(LocalDate data);

    // Buscar financeiros entre duas datas de vencimento
    List<Financeiro> findByDataVencimentoBetween(LocalDate inicio, LocalDate fim);
}