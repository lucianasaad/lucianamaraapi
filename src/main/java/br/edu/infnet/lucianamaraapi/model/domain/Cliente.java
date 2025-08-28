package br.edu.infnet.lucianamaraapi.model.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@Entity
public class Cliente extends Pessoa {

	@Min(value = 0, message = "Média de atraso não pode ser negativa.")
	private int mediaAtraso;

	@PositiveOrZero(message = "Saldo a receber não pode ser negativo.")
	private double saldoReceber;

	private LocalDate dataProxVencimento;

	private boolean ativo;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "endereco_cobranca_id")
	private Endereco enderecoCobranca;

	@Override
	public String toString() {

		return String.format("%s - %d - %.2f - %s - %s - %s",
				super.toString(), mediaAtraso, saldoReceber, dataProxVencimento, ativo ? "ativo" : "inativo", enderecoCobranca);
	}

	@Override
	public String obterTipo() {
		return "Cliente";
	}

	public int getMediaAtraso() {
		return mediaAtraso;
	}

	public void setMediaAtraso(int mediaAtraso) {
		this.mediaAtraso = mediaAtraso;
	}

	public double getSaldoReceber() {
		return saldoReceber;
	}

	public void setSaldoReceber(double saldoReceber) {
		this.saldoReceber = saldoReceber;
	}

	public LocalDate getDataProxVencimento() {
		return dataProxVencimento;
	}

	public void setDataProxVencimento(LocalDate dataProxVencimento) {
		this.dataProxVencimento = dataProxVencimento;
	}

	public Endereco getEnderecoCobranca() {
		return enderecoCobranca;
	}

	public void setEnderecoCobranca(Endereco enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
