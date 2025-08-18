package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.ClienteInvalidoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.ClienteNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClienteService implements CrudService<Cliente, Integer> {

	private final Map<Integer, Cliente> mapa = new ConcurrentHashMap<>();
	private final AtomicInteger nextId = new AtomicInteger(1);

	private void validar(Cliente cliente) {
		if (cliente == null) {
			throw new IllegalArgumentException("O cliente não pode estar nulo!");
		}

		if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
			throw new ClienteInvalidoException("O nome do cliente deve ser informado!");
		}
	}

	@Override
	public Cliente incluir(Cliente cliente) {

		validar(cliente);

		if (cliente.getId() != null && cliente.getId() != 0) {
			throw new IllegalArgumentException("Um novo Cliente não pode ter um ID na inclusão!");
		}

		cliente.setId(nextId.getAndIncrement());
		mapa.put(cliente.getId(), cliente);

		return cliente;
	}

	@Override
	public Cliente alterar(Integer id, Cliente cliente) {

		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
		}

		validar(cliente);
		buscarPorId(id);

		cliente.setId(id);
		mapa.put(cliente.getId(), cliente);

		return cliente;
	}

	@Override
	public void excluir(Integer id) {
		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
		}

		if (!mapa.containsKey(id)) {
			throw new ClienteNaoEncontradoException("O Cliente com ID " + id + " não foi encontrado!");
		}

		mapa.remove(id);
	}

	@Override
	public List<Cliente> listarTodos() {
		return new ArrayList<>(mapa.values());
	}

	@Override
	public Cliente buscarPorId(Integer id) {
		Cliente cliente = mapa.get(id);

		if (cliente == null) {
			throw new ClienteNaoEncontradoException("Cliente ID " + id + " não encontrado!");
		}

		return cliente;
	}

	public Cliente inativar(Integer id) {

		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para inativação não pode ser nulo/zero!");
		}

		Cliente cliente = buscarPorId(id);

		if(!cliente.isAtivo()) {
			System.out.println("Cliente " + cliente.getNome() + " já está inativo!");
			return cliente;
		}

		cliente.setAtivo(false);

		mapa.put(cliente.getId(), cliente);

		return cliente;
	}

	public Cliente atualizarSaldoReceber(Cliente cliente, double valor) {

		double saldoAtual = cliente.getSaldoReceber() != 0 ? cliente.getSaldoReceber() : 0.0;
		cliente.setSaldoReceber(saldoAtual + (valor));
		alterar(cliente.getId(), cliente);
		return cliente;
	}

}