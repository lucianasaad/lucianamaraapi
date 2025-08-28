package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/financeiros")
public class FinanceiroController {

	private final FinanceiroService financeiroService;

	public FinanceiroController(FinanceiroService financeiroService) {
		this.financeiroService = financeiroService;
	}

	@PostMapping
	public ResponseEntity<Financeiro> incluir(@Valid @RequestBody Financeiro financeiro) {
		Financeiro novoFinanceiro = financeiroService.incluir(financeiro);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoFinanceiro);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Financeiro> alterar(@PathVariable Integer id, @RequestBody Financeiro financeiro) {
		Financeiro financeiroAlterado = financeiroService.alterar(id, financeiro);
		return ResponseEntity.ok(financeiroAlterado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		financeiroService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/baixar")
	public ResponseEntity<Financeiro> baixar(@PathVariable Integer id) {
		Financeiro financeiro = financeiroService.baixar(id);
		return ResponseEntity.ok(financeiro);
	}

	@GetMapping
	public ResponseEntity<List<Financeiro>> listarTodos() {
		List<Financeiro> lista = financeiroService.listarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Financeiro> buscarPorId(@PathVariable Integer id) {
		Financeiro financeiro = financeiroService.buscarPorId(id);
		return ResponseEntity.ok(financeiro);
	}

	@GetMapping("/vencimento")
	public ResponseEntity<List<Financeiro>> buscarPorDataVencimento(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

		List<Financeiro> resultados = financeiroService.buscarPorDataVencimento(inicio, fim);
		return ResponseEntity.ok(resultados);
	}
}
