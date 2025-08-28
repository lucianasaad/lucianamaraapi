package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//@Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. Use o formato 99999-999.")
	private String cep;

	//@NotBlank(message = "Logradouro é obrigatório.")
	private String logradouro;

	//@Positive(message = "Número deve ser maior que zero.")
	private Integer numero;

	private String complemento;

	//@NotBlank(message = "Bairro é obrigatório.")
	private String bairro;

	//@NotBlank(message = "Localidade é obrigatória.")
	private String localidade;

	//@NotBlank(message = "UF é obrigatória.")
	//@Size(min = 2, max = 2, message = "UF deve ter 2 caracteres.")
	private String uf;

	//@NotBlank(message = "Estado é obrigatório.")
	private String estado;

	//TODO construtor do endereço

	@Override
	public String toString() {
		return "Endereco{" +
				"id=" + id +
				", cep='" + cep + '\'' +
				", logradouro='" + logradouro + '\'' +
				", numero='" + numero + '\'' +
				", complemento='" + complemento + '\'' +
				", bairro='" + bairro + '\'' +
				", localidade='" + localidade + '\'' +
				", uf='" + uf + '\'' +
				", estado='" + estado + '\'' +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}