package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.service.EnderecoService;
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
	public Endereco incluir(@RequestBody Endereco endereco) {
		return enderecoService.incluir(endereco);
	}

	@PutMapping("/{id}")
	public Endereco alterar(@PathVariable Integer id, @RequestBody Endereco endereco) {
		return enderecoService.alterar(id, endereco);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		enderecoService.excluir(id);
	}

	@GetMapping
	public List<Endereco> listarTodos() {
		return enderecoService.listarTodos();
	}

	@GetMapping("/{id}")
	public Endereco buscarPorId(@PathVariable Integer id) {
		return enderecoService.buscarPorId(id);
	}
}
