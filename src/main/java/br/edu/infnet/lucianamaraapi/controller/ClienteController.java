package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.dto.ClienteFinanceiroDTO;
import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.service.ClienteService;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;
import jakarta.validation.Valid;
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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteService clienteService;
	private final FinanceiroService financeiroService;

	public ClienteController(ClienteService clienteService, FinanceiroService financeiroService) {
		this.clienteService = clienteService;
		this.financeiroService = financeiroService;
	}

	@PostMapping
	public ResponseEntity<Cliente> incluir(@Valid @RequestBody Cliente cliente) {
		Cliente novo = clienteService.incluir(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(novo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> alterar(@PathVariable Integer id, @RequestBody Cliente cliente) {
		Cliente alterado = clienteService.alterar(id, cliente);
		return ResponseEntity.ok(alterado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> listarTodos() {
		List<Cliente> lista = clienteService.listarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscarPorId(id);
		return ResponseEntity.ok(cliente);
	}

	@PatchMapping("/{id}/inativar")
	public ResponseEntity<Cliente> inativar(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscarPorId(id);
		cliente.setAtivo(false);
		Cliente atualizado = clienteService.alterar(id, cliente);
		return ResponseEntity.ok(atualizado);
	}

	@PatchMapping("/{id}/atualizarsaldoreceber")
	public ResponseEntity<Cliente> atualizarSaldo(@PathVariable Integer id,
												  @RequestParam double valor) {
		Cliente cliente = clienteService.buscarPorId(id);
		Cliente atualizado = clienteService.atualizarSaldoReceber(cliente, valor);
		return ResponseEntity.ok(atualizado);
	}

	@GetMapping("/{id}/saldo")
	public ResponseEntity<Double> consultarSaldo(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscarPorId(id);
		return ResponseEntity.ok(cliente.getSaldoReceber());
	}

	@GetMapping("/{id}/financeiros")
	public ResponseEntity<ClienteFinanceiroDTO> consultarFinanceirosCliente(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscarPorId(id);

		List<Financeiro> financeirosDoCliente = financeiroService.listarTodos().stream()
				.filter(financeiro ->
						financeiro.getCliente() != null && financeiro.getCliente().getId().equals(id)
				)
				.collect(Collectors.toList());

		ClienteFinanceiroDTO dto = new ClienteFinanceiroDTO(
				cliente.getId(),
				cliente.getNome(),
				cliente.getSaldoReceber(),
				financeirosDoCliente
		);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/buscarPorNome")
	public ResponseEntity<Collection<Cliente>> buscarPorNome(@RequestParam String nome) {
		List<Cliente> clientes = clienteService.buscarPorNome(nome);

		if (clientes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(clientes);
	}
}
