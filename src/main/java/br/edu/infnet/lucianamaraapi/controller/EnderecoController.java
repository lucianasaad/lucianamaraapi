package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.service.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

	private final EnderecoService enderecoService;

	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@PostMapping
	public ResponseEntity<Endereco> incluir(@RequestBody Endereco endereco) {
		Endereco novo = enderecoService.incluir(endereco);
		return ResponseEntity.status(HttpStatus.CREATED).body(novo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Endereco> alterar(@PathVariable Integer id, @RequestBody Endereco endereco) {
		Endereco alterado = enderecoService.alterar(id, endereco);
		return ResponseEntity.ok(alterado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		enderecoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Endereco>> listarTodos() {
		List<Endereco> lista = enderecoService.listarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> buscarPorId(@PathVariable Integer id) {
		Endereco endereco = enderecoService.buscarPorId(id);
		return ResponseEntity.ok(endereco);
	}
}
