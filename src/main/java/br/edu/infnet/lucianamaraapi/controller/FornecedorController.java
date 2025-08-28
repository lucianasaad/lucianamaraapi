package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.service.FornecedorService;
import br.edu.infnet.lucianamaraapi.model.dto.FornecedorFinanceiroDTO;
import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

	private final FornecedorService fornecedorService;
	private final FinanceiroService financeiroService;

	public FornecedorController(FornecedorService fornecedorService, FinanceiroService financeiroService) {
		this.fornecedorService = fornecedorService;
		this.financeiroService = financeiroService;
	}

	@PostMapping
	public ResponseEntity<Fornecedor> incluir(@RequestBody Fornecedor fornecedor) {
		Fornecedor novo = fornecedorService.incluir(fornecedor);
		return ResponseEntity.status(HttpStatus.CREATED).body(novo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> alterar(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
		Fornecedor alterado = fornecedorService.alterar(id, fornecedor);
		return ResponseEntity.ok(alterado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		fornecedorService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Fornecedor>> listarTodos() {
		List<Fornecedor> lista = fornecedorService.listarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);
		return ResponseEntity.ok(fornecedor);
	}

	@PatchMapping("/{id}/atualizarsaldodevedor")
	public ResponseEntity<Fornecedor> atualizarSaldo(@PathVariable Integer id,
													 @RequestParam double valor) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);
		Fornecedor atualizado = fornecedorService.atualizarSaldoDevedor(fornecedor, valor);
		return ResponseEntity.ok(atualizado);
	}

	@GetMapping("/{id}/saldo")
	public ResponseEntity<Double> consultarSaldo(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);
		return ResponseEntity.ok(fornecedor.getSaldoDevedor());
	}

	@GetMapping("/{id}/financeiros")
	public ResponseEntity<FornecedorFinanceiroDTO> consultarFinanceirosFornecedor(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);

		List<Financeiro> financeirosDoFornecedor = financeiroService.listarTodos().stream()
				.filter(financeiro ->
						financeiro.getFornecedor() != null && financeiro.getFornecedor().getId().equals(id)
				)
				.collect(Collectors.toList());

		FornecedorFinanceiroDTO dto = new FornecedorFinanceiroDTO(
				fornecedor.getId(),
				fornecedor.getNome(),
				fornecedor.getSaldoDevedor(),
				financeirosDoFornecedor,
				null
		);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/{id}/financeiros/categorias")
	public ResponseEntity<FornecedorFinanceiroDTO> consultarFinanceirosFornecedorPorCategoria(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);

		List<Financeiro> financeirosDoFornecedor = financeiroService.listarTodos().stream()
				.filter(financeiro ->
						financeiro.getFornecedor() != null && financeiro.getFornecedor().getId().equals(id)
				)
				.collect(Collectors.toList());

		Map<String, List<Financeiro>> financeirosPorCategoria = financeirosDoFornecedor.stream()
				.collect(Collectors.groupingBy(Financeiro::getNatureza));

		FornecedorFinanceiroDTO dto = new FornecedorFinanceiroDTO(
				fornecedor.getId(),
				fornecedor.getNome(),
				fornecedor.getSaldoDevedor(),
				null,
				financeirosPorCategoria
		);
		return ResponseEntity.ok(dto);
	}
}
