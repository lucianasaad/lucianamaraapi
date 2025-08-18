package br.edu.infnet.lucianamaraapi.controller;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.service.ClienteService;
import org.springframework.web.bind.annotation.*;
import br.edu.infnet.lucianamaraapi.dto.ClienteFinanceiroDTO;
import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;
import java.util.stream.Collectors;
import java.util.List;

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
	public Cliente incluir(@RequestBody Cliente cliente) {
		return clienteService.incluir(cliente);
	}

	@PutMapping("/{id}")
	public Cliente alterar(@PathVariable Integer id, @RequestBody Cliente cliente) {
		return clienteService.alterar(id, cliente);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Integer id) {
		clienteService.excluir(id);
	}

	@GetMapping
	public List<Cliente> listarTodos() {
		return clienteService.listarTodos();
	}

	@GetMapping("/{id}")
	public Cliente buscarPorId(@PathVariable Integer id) {
		return clienteService.buscarPorId(id);
	}

	// Novo endpoint para inativar cliente
	@PatchMapping("/{id}/inativar")
	public Cliente inativar(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscarPorId(id);
		cliente.setAtivo(false);
		return clienteService.alterar(id, cliente);
	}

	@PatchMapping("/{id}/atualizarsaldoreceber")
	public Cliente atualizarSaldo(@PathVariable Integer id,
								  @RequestParam double valor) {
		Cliente cliente = clienteService.buscarPorId(id);
		return clienteService.atualizarSaldoReceber(cliente, valor);
	}

	// Retorna apenas o saldo a receber do cliente
	@GetMapping("/{id}/saldo")
	public double consultarSaldo(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscarPorId(id);
		return cliente.getSaldoReceber();
	}

	@GetMapping("/{id}/financeiros")
	public ClienteFinanceiroDTO consultarFinanceirosCliente(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscarPorId(id);

		List<Financeiro> financeirosDoCliente = financeiroService.listarTodos().stream()
				.filter(financeiro -> financeiro.getPessoaRelacionada() instanceof Cliente clienteFin && clienteFin.getId().equals(id))
				.collect(Collectors.toList());

		return new ClienteFinanceiroDTO(
				cliente.getId(),
				cliente.getNome(),
				cliente.getSaldoReceber(),
				financeirosDoCliente
		);
	}
}
