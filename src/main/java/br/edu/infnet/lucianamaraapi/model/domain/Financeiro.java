package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Financeiro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER) // muitos financeiros para uma empresa
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;

	private String natureza;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = true)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id", nullable = true)
	private Fornecedor fornecedor;

	private TipoFinanceiro tipoFinanceiro;
	private double valor;
	private LocalDate dataLancamento;
	private LocalDate dataVencimento;
	private LocalDate dataBaixa;
	private StatusFinanceiro status;

	public enum TipoFinanceiro {
		RECEBER(1),
		PAGAR(-1);

		private final int codigo;

		TipoFinanceiro(int codigo) {
			this.codigo = codigo;
		}

		public int getCodigo() {
			return codigo;
		}

		public static TipoFinanceiro fromCodigo(int codigo) {
			for (TipoFinanceiro tipo : values()) {
				if (tipo.codigo == codigo) {
					return tipo;
				}
			}
			throw new IllegalArgumentException("Tipo de Financeiro inválido: " + codigo);
		}
	}

	public enum StatusFinanceiro {
		ABERTO('A'),
		BAIXADO('B');

		private final char codigo;

		StatusFinanceiro(char codigo) {
			this.codigo = codigo;
		}

		public char getCodigo() {
			return codigo;
		}

		public static StatusFinanceiro fromCodigo(char codigo) {
			for (StatusFinanceiro status : values()) {
				if (status.codigo == codigo) {
					return status;
				}
			}
			throw new IllegalArgumentException("Código de status inválido: " + codigo);
		}
	}

	public boolean isAberto() {
		return status == StatusFinanceiro.ABERTO;
	}

	public boolean isBaixado() {
		return status == StatusFinanceiro.BAIXADO;
	}

	public boolean isRecebimento() {
		return tipoFinanceiro == TipoFinanceiro.RECEBER;
	}

	public boolean isPagamento() {
		return tipoFinanceiro == TipoFinanceiro.PAGAR;
	}

	@Override
	public String toString() {

		String empresa_id = (empresa != null && empresa.getId() != null) ? empresa.getId().toString() : "N/A";
		String nomeCliente = (cliente != null && cliente.getId() != null) ? cliente.getNome().toString() : "N/A";
		String nomeFornecedor = (fornecedor != null && fornecedor.getId() != null) ? fornecedor.getNome().toString() : "N/A";

		return "Financeiro{" +
					"id=" + id +
					", empresa='" + empresa_id + '\'' +
					", natureza='" + (natureza != null ? natureza : "N/A") + '\'' +
					", cliente='" + nomeCliente + '\'' +
					", fornecedor='" + nomeFornecedor + '\'' +
					", tipoFinanceiro=" + (tipoFinanceiro != null ? tipoFinanceiro.name() : "N/A") +
					", valor=" + valor +
					", dataLancamento=" + (dataLancamento != null ? dataLancamento : "N/A") +
					", dataVencimento=" + (dataVencimento != null ? dataVencimento : "N/A") +
					", dataBaixa=" + (dataBaixa != null ? dataBaixa : "N/A") +
					", status=" + (status != null ? status.name() : "N/A") +
					'}';
	}

	// Getters e setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public TipoFinanceiro getTipoFinanceiro() {
		return tipoFinanceiro;
	}

	public void setTipoFinanceiro(TipoFinanceiro tipoFinanceiro) {
		this.tipoFinanceiro = tipoFinanceiro;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(LocalDate dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public StatusFinanceiro getStatus() {
		return status;
	}

	public void setStatus(StatusFinanceiro status) {
		this.status = status;
	}

	public boolean isFinanceiroEmAberto() {
		return (this.isAberto()) && (this.getDataBaixa() == null);
	}

	public void baixarFinanceiro() {
		this.setDataBaixa(LocalDate.now());
		this.setStatus(StatusFinanceiro.BAIXADO);
	}

}
