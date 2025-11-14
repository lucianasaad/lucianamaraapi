package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.client.MovimentacaoBancariaClient;
import br.edu.infnet.lucianamaraapi.client.dto.SaldoConvertidoResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class IntegracaoBancariaService {

    private final MovimentacaoBancariaClient movClient;

    // "Implantação" de saldo local só para visualização
    private final Map<Integer, BigDecimal> saldosIniciais = new HashMap<>();

    public IntegracaoBancariaService(MovimentacaoBancariaClient movClient) {
        this.movClient = movClient;
    }

    @PostConstruct
    public void carregarSaldosIniciais() {
        saldosIniciais.put(1, new BigDecimal("1500.00"));
        saldosIniciais.put(2, new BigDecimal("2750.50"));
        saldosIniciais.put(3, new BigDecimal("500.00"));
    }

    public SaldoConvertidoResponse consultarSaldoConvertido(Integer contaId, String moeda) {

        if (contaId == null) {
            throw new IllegalArgumentException("contaId é obrigatório");
        }

        if (!StringUtils.hasText(moeda)) {
            throw new IllegalArgumentException("moeda é obrigatória");
        }

        SaldoConvertidoResponse remoto = movClient.obterSaldoConvertido(contaId, moeda);

        BigDecimal saldoLocal = saldosIniciais.getOrDefault(contaId, remoto.getSaldoOriginal());
        remoto.setSaldoOriginal(saldoLocal);

        // 3) Recalcula o saldo convertido com base nesse saldo local
        if (saldoLocal != null && remoto.getTaxaConversao() != null) {
            BigDecimal saldoConvertido = saldoLocal.multiply(remoto.getTaxaConversao());
            remoto.setSaldoConvertido(saldoConvertido);
        }

        return remoto;
    }
}
