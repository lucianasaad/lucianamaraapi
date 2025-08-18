package br.edu.infnet.lucianamaraapi.model.domain;

import java.time.LocalDate;

public class Financeiro {

	private Integer id;
	private Empresa empresa;
	private String natureza;
	private Pessoa pessoaRelacionada;   // Cliente ou Fornecedor

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
		return String.format(
				"Financeiro[id=%d, empresa=%s, natureza=%s, pessoaRelacionada=%s, tipoFinanceiro=%s, valor=%.2f, dataLancamento=%s, dataVencimento=%s, dataBaixa=%s, status=%s]",
				id,
				empresa != null ? empresa.getNome() : "N/A",
				natureza != null ? natureza : "N/A",
				pessoaRelacionada != null ? pessoaRelacionada.getNome() : "N/A",
				tipoFinanceiro != null ? tipoFinanceiro.name() : "N/A",
				valor,
				dataLancamento != null ? dataLancamento.toString() : "N/A",
				dataVencimento != null ? dataVencimento.toString() : "N/A",
				dataBaixa != null ? dataBaixa.toString() : "N/A",
				status != null ? status.name() : "N/A"
		);
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

	public Pessoa getPessoaRelacionada() {
		return pessoaRelacionada;
	}

	public void setPessoaRelacionada(Pessoa pessoaRelacionada) {
		this.pessoaRelacionada = pessoaRelacionada;
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
