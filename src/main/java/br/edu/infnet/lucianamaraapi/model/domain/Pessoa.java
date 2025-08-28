package br.edu.infnet.lucianamaraapi.model.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


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

@MappedSuperclass
public abstract class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "O nome é obrigatório.")
	@Column(nullable = false, length = 100)
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private TipoPessoa tipoPessoa;

	@NotBlank(message = "Documento é obrigatório (CPF ou CNPJ).")
	@Column(unique = true, nullable = false, length = 20)
	private String documento;

	@NotBlank(message = "O e-mail é obrigatório.")
	@Email(message = "Formato de e-mail inválido.")
	@Column(nullable = false, length = 120)
	private String email;

	@NotBlank(message = "O telefone é obrigatório.")
	@Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}",
			message = "Telefone inválido. Use o formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX.")
	private String telefone;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "endereco_id")
	@Valid
	private Endereco endereco;

	public enum TipoPessoa {
		FISICA("Pessoa Física"),
		JURIDICA("Pessoa Jurídica");

		private final String descricao;

		TipoPessoa(String descricao) { this.descricao = descricao; }
		public String getDescricao() { return descricao; }
	}

	public abstract String obterTipo();

	@Override
	public String toString() {
		return String.format("%d - %s - %s - %s - %s - %s", id, nome, documento, email, telefone, endereco);
	}

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
		//	throw new IllegalArgumentException("Tipo Pessoa não pode ser nulo");
	//	}
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
