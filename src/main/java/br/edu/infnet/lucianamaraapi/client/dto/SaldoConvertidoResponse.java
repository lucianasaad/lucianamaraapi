package br.edu.infnet.lucianamaraapi.client.dto;

import java.math.BigDecimal;

public class SaldoConvertidoResponse {

    private Integer contaId;
    private String moedaOrigem;
    private String moedaDestino;
    private BigDecimal saldoOriginal;
    private BigDecimal taxaConversao;
    private BigDecimal saldoConvertido;

    public Integer getContaId() {
        return contaId;
    }

    public void setContaId(Integer contaId) {
        this.contaId = contaId;
    }

    public String getMoedaOrigem() {
        return moedaOrigem;
    }

    public void setMoedaOrigem(String moedaOrigem) {
        this.moedaOrigem = moedaOrigem;
    }

    public String getMoedaDestino() {
        return moedaDestino;
    }

    public void setMoedaDestino(String moedaDestino) {
        this.moedaDestino = moedaDestino;
    }

    public BigDecimal getSaldoOriginal() {
        return saldoOriginal;
    }

    public void setSaldoOriginal(BigDecimal saldoOriginal) {
        this.saldoOriginal = saldoOriginal;
    }

    public BigDecimal getTaxaConversao() {
        return taxaConversao;
    }

    public void setTaxaConversao(BigDecimal taxaConversao) {
        this.taxaConversao = taxaConversao;
    }

    public BigDecimal getSaldoConvertido() {
        return saldoConvertido;
    }

    public void setSaldoConvertido(BigDecimal saldoConvertido) {
        this.saldoConvertido = saldoConvertido;
    }
}
