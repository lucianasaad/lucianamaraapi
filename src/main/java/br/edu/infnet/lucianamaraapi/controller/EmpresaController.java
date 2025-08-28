package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Empresa> incluir(@RequestBody Empresa empresa) {
		Empresa nova = empresaService.incluir(empresa);
		return ResponseEntity.status(HttpStatus.CREATED).body(nova);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Empresa> alterar(@PathVariable Integer id, @RequestBody Empresa empresa) {
		Empresa alterada = empresaService.alterar(id, empresa);
		return ResponseEntity.ok(alterada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		empresaService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Empresa>> listarTodos() {
		List<Empresa> lista = empresaService.listarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empresa> buscarPorId(@PathVariable Integer id) {
		Empresa empresa = empresaService.buscarPorId(id);
		return ResponseEntity.ok(empresa);
	}
}
