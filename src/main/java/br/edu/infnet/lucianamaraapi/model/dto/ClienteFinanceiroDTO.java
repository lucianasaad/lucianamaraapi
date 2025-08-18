package br.edu.infnet.lucianamaraapi.dto;

import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;

import java.util.List;

public class ClienteFinanceiroDTO {

	private Integer clienteId;
	private String clienteNome;
	private double saldoReceber;
	private List<Financeiro> financeiros;

	public ClienteFinanceiroDTO(Integer clienteId, String clienteNome, double saldoReceber, List<Financeiro> financeiros) {
		this.clienteId = clienteId;
		this.clienteNome = clienteNome;
		this.saldoReceber = saldoReceber;
		this.financeiros = financeiros;
	}

	// Getters e Setters
	public Integer getClienteId() { return clienteId; }
	public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

	public String getClienteNome() { return clienteNome; }
	public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

	public double getSaldoReceber() { return saldoReceber; }
	public void setSaldoReceber(double saldoDevedor) { this.saldoReceber = saldoReceber; }

	public List<Financeiro> getFinanceiros() { return financeiros; }
	public void setFinanceiros(List<Financeiro> financeiros) { this.financeiros = financeiros; }
}
