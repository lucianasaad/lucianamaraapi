package br.edu.infnet.lucianamaraapi.client;

import br.edu.infnet.lucianamaraapi.client.dto.SaldoConvertidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "movimentacaoBancariaClient",
        url = "http://localhost:8081" // onde o lucianamaramovbancariaapi está rodando
)
public interface MovimentacaoBancariaClient {

    @GetMapping("/api/contas/{contaId}/saldo-convertido")
    SaldoConvertidoResponse obterSaldoConvertido(
            @PathVariable("contaId") Integer contaId,
            @RequestParam("moeda") String moeda
    );
}
