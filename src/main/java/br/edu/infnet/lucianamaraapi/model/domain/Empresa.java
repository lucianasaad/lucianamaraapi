package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Empresa extends Pessoa {

	@NotBlank(message = "Razão social é obrigatória.")
	private String razaoSocial;

	@NotBlank(message = "Nome fantasia é obrigatório.")
	private String nomeFantasia;

	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Financeiro> financeiros = new ArrayList<Financeiro>();

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
