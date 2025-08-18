package br.edu.infnet.lucianamaraapi.model.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "tipoClasse"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = Cliente.class, name = "Cliente"),
		@JsonSubTypes.Type(value = Fornecedor.class, name = "Fornecedor"),
		@JsonSubTypes.Type(value = Empresa.class, name = "Empresa")
})

public abstract class Pessoa {

	private Integer id;
	private String nome;
	private TipoPessoa tipoPessoa;
	private String documento; // CPF ou CNPJ
	private String email;
	private String telefone;
	private Endereco endereco;

	public enum TipoPessoa {
		FISICA("F", "Pessoa Física"),
		JURIDICA("J", "Pessoa Jurídica");

		private final String codigo;
		private final String descricao;

		TipoPessoa(String codigo, String descricao) {
			this.codigo = codigo;
			this.descricao = descricao;
		}

		@JsonValue
		public String getCodigo() {
			return codigo;
		}

		public String getDescricao() {
			return descricao;
		}

		@JsonCreator
		public static TipoPessoa fromCodigo(String codigo) {
			for (TipoPessoa tipo : values()) {
				if (tipo.codigo.equalsIgnoreCase(codigo) || tipo.name().equalsIgnoreCase(codigo)) {
					return tipo;
				}
			}
			throw new IllegalArgumentException("Código inválido: " + codigo);
		}
	}

	@Override
	public String toString() {

			return String.format("%d - %s - %s - %s - %s", id, nome, documento, email, endereco);
		}

	public abstract String obterTipo();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		//if (tipoPessoa == null) {
	//		throw new IllegalArgumentException("Tipo Pessoa não pode ser nulo");
//		}
		this.tipoPessoa = tipoPessoa;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
