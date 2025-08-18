package br.edu.infnet.lucianamaraapi.model.dto;

import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import java.util.List;
import java.util.Map;

public class FornecedorFinanceiroDTO {

	private Integer id;
	private String nome;
	private Double saldoDevedor;

	// Lista de todos os financeiros do fornecedor
	private List<Financeiro> financeiros;

	// Mapa agrupado por categoria (ex.: Receita, Despesa, etc.)
	private Map<String, List<Financeiro>> financeirosPorCategoria;

	public FornecedorFinanceiroDTO(Integer id, String nome, Double saldoDevedor,
								   List<Financeiro> financeiros,
								   Map<String, List<Financeiro>> financeirosPorCategoria) {
		this.id = id;
		this.nome = nome;
		this.saldoDevedor = saldoDevedor;
		this.financeiros = financeiros;
		this.financeirosPorCategoria = financeirosPorCategoria;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Double getSaldoDevedor() {
		return saldoDevedor;
	}

	public List<Financeiro> getFinanceiros() {
		return financeiros;
	}

	public Map<String, List<Financeiro>> getFinanceirosPorCategoria() {
		return financeirosPorCategoria;
	}

}
