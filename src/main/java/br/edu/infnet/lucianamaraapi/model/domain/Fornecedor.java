package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Fornecedor extends Pessoa {

	private String categoria;          // Ex: Energia, Telecom, Serviços
	private String site;
	private String contatoResponsavel;
	private double saldoDevedor;

	@Override
	public String toString() {
		return String.format("%s - Categoria: %s - Site: %s - Contato Responsável: %s",
				super.toString(), categoria, site != null ? site : "N/A", contatoResponsavel);
	}

	@Override
	public String obterTipo() {	return "Fornecedor"; }

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getContatoResponsavel() {
		return contatoResponsavel;
	}

	public void setContatoResponsavel(String contatoResponsavel) {
		this.contatoResponsavel = contatoResponsavel;
	}

	public double getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(double saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}
}
