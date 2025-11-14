package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.client.dto.SaldoConvertidoResponse;
import br.edu.infnet.lucianamaraapi.model.service.IntegracaoBancariaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/integracao/bancaria")
public class IntegracaoBancariaController {

    private final IntegracaoBancariaService integracaoBancariaService;

    public IntegracaoBancariaController(IntegracaoBancariaService integracaoBancariaService) {
        this.integracaoBancariaService = integracaoBancariaService;
    }

    @GetMapping("/contas/{contaId}/saldo-convertido")
    public SaldoConvertidoResponse consultarSaldoConvertido(
            @PathVariable Integer contaId,
            @RequestParam String moeda
    ) {
        return integracaoBancariaService.consultarSaldoConvertido(contaId, moeda);
    }
}
