package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.service.EmpresaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

	private final EmpresaService empresaService;

	public EmpresaController(EmpresaService empresaService) {
		this.empresaService = empresaService;
	}

	@PostMapping
	public Empresa incluir(@RequestBody Empresa empresa) {
		return empresaService.incluir(empresa);
	}

	@PutMapping(value = "/{id}")
	public Empresa alterar(@PathVariable Integer id, @RequestBody Empresa empresa) {
		return empresaService.alterar(id, empresa);
	}

	@DeleteMapping(value = "/{id}")
	public void excluir(@PathVariable Integer id) {
		empresaService.excluir(id);
	}

	@GetMapping
	public List<Empresa> listarTodos(){
		return empresaService.listarTodos();
	}

	@GetMapping(value = "/{id}")
	public Empresa buscarPorId(@PathVariable Integer id) {
		return empresaService.buscarPorId(id);
	}
}
