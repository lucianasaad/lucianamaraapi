package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financeiros")
public class FinanceiroController {

	private final FinanceiroService financeiroService;
	
	public FinanceiroController(FinanceiroService financeiroService) {
		this.financeiroService = financeiroService;
	}
	
	@PostMapping
	public Financeiro incluir(@RequestBody Financeiro financeiro) {
		return financeiroService.incluir(financeiro);
	}
		
	@PutMapping(value = "/{id}")
	public Financeiro alterar(@PathVariable Integer id, @RequestBody Financeiro financeiro) {
		return financeiroService.alterar(id, financeiro);
	}
	
	@DeleteMapping(value = "/{id}")
	public void excluir(@PathVariable Integer id) {
		financeiroService.excluir(id);
	}
	
	@PatchMapping(value = "/{id}/baixar")
	public Financeiro baixar(@PathVariable Integer id) {
		return financeiroService.baixar(id);
	}
	
	@GetMapping
	public List<Financeiro> listarTodos(){
		return financeiroService.listarTodos();
	}
	
	@GetMapping(value = "/{id}")
	public Financeiro buscarPorId(@PathVariable Integer id) {
		return financeiroService.buscarPorId(id);
	}


}