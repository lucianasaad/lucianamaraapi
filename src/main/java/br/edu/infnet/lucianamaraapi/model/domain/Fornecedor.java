package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Fornecedor extends Pessoa {

	@NotBlank(message = "Categoria é obrigatória.")
	private String categoria;

	//@URL(message = "Formato de site inválido.")
	private String site;

	private String contatoResponsavel;

	@PositiveOrZero(message = "Saldo devedor não pode ser negativo.")
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
