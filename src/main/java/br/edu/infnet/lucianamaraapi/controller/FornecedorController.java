package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.service.FornecedorService;
import org.springframework.web.bind.annotation.*;
import br.edu.infnet.lucianamaraapi.model.dto.FornecedorFinanceiroDTO;
import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;

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
	public Fornecedor incluir(@RequestBody Fornecedor fornecedor) {
		return fornecedorService.incluir(fornecedor);
	}

	@PutMapping("/{id}")
	public Fornecedor alterar(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
		return fornecedorService.alterar(id, fornecedor);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		fornecedorService.excluir(id);
	}

	@GetMapping
	public List<Fornecedor> listarTodos() {
		return fornecedorService.listarTodos();
	}

	@GetMapping("/{id}")
	public Fornecedor buscarPorId(@PathVariable Integer id) {
		return fornecedorService.buscarPorId(id);
	}

	@PatchMapping("/{id}/atualizarsaldodevedor")
	public Fornecedor atualizarSaldo(@PathVariable Integer id,
									 @RequestParam double valor) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);
		return fornecedorService.atualizarSaldoDevedor(fornecedor, valor);
	}

	// Retorna apenas o saldo devedor do fornecedor
	@GetMapping("/{id}/saldo")
	public double consultarSaldo(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);
		return fornecedor.getSaldoDevedor();
	}

	@GetMapping("/{id}/financeiros")
	public FornecedorFinanceiroDTO consultarFinanceirosFornecedor(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);

		List<Financeiro> financeirosDoFornecedor = financeiroService.listarTodos().stream()
				.filter(financeiro ->
						financeiro.getfornecedor() != null && financeiro.getfornecedor().getId().equals(id)
				)
				.collect(Collectors.toList());

		return new FornecedorFinanceiroDTO(
				fornecedor.getId(),
				fornecedor.getNome(),
				fornecedor.getSaldoDevedor(),
				financeirosDoFornecedor,
				null // aqui deixamos null porque este endpoint NÃO agrupa por categoria
		);
	}

	@GetMapping("/{id}/financeiros/categorias")
	public FornecedorFinanceiroDTO consultarFinanceirosFornecedorPorCategoria(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.buscarPorId(id);

		List<Financeiro> financeirosDoFornecedor = financeiroService.listarTodos().stream()
				.filter(financeiro ->
						financeiro.getfornecedor() != null && financeiro.getfornecedor().getId().equals(id)
				)
				.collect(Collectors.toList());

		// Agrupa os financeiros por categoria/natureza
		Map<String, List<Financeiro>> financeirosPorCategoria = financeirosDoFornecedor.stream()
				.collect(Collectors.groupingBy(Financeiro::getNatureza));

		return new FornecedorFinanceiroDTO(
				fornecedor.getId(),
				fornecedor.getNome(),
				fornecedor.getSaldoDevedor(),
				null,
				financeirosPorCategoria
		);
	}}
