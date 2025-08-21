package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Empresa extends Pessoa {

	private String razaoSocial;
	private String nomeFantasia;

	@Override
	public String toString() {
		return String.format("%s - Razão Social: %s - Nome Fantasia: %s",
				super.toString(),
				razaoSocial != null ? razaoSocial : "N/A",
				nomeFantasia != null ? nomeFantasia : "N/A");
	}

	@Override
	public String obterTipo() { return "Empresa"; }

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

}
