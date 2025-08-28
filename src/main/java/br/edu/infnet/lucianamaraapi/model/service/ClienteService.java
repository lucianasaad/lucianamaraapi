package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.ClienteInvalidoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.ClienteNaoEncontradoException;
import br.edu.infnet.lucianamaraapi.model.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements CrudService<Cliente, Integer> {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	private void validar(Cliente cliente) {
		if (cliente == null) {
			throw new IllegalArgumentException("O cliente não pode estar nulo!");
		}

		if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
			throw new ClienteInvalidoException("O nome do cliente deve ser informado!");
		}
	}

	@Override
	@Transactional
	public Cliente incluir(Cliente cliente) {

		validar(cliente);

		if (cliente.getId() != null && cliente.getId() != 0) {
			throw new IllegalArgumentException("Um novo Cliente não pode ter um ID na inclusão!");
		}

		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional
	public Cliente alterar(Integer id, Cliente cliente) {

		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
		}

		validar(cliente);
		buscarPorId(id);

		cliente.setId(id);

		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		Cliente cliente = buscarPorId(id);
		clienteRepository.delete(cliente);
	}

	@Override
	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Integer id) {

		if (id == null || id <= 0) {
			throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
		}

		return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException("O Cliente com ID " + id + " não foi encontrado!"));
	}

	@Transactional
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
		return clienteRepository.save(cliente);

	}

	@Transactional
	public Cliente atualizarSaldoReceber(Cliente cliente, double valor) {

		if (cliente == null) {
			throw new IllegalArgumentException("O cliente não pode ser nulo!");
		}

		double saldoAtual = cliente.getSaldoReceber() != 0 ? cliente.getSaldoReceber() : 0.0;
		cliente.setSaldoReceber(saldoAtual + (valor));
		return clienteRepository.save(cliente);
	}

	@Transactional
	public Cliente buscarPorDocumento(String documento) {
		return clienteRepository.findByDocumento(documento)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com documento " + documento + " não encontrado!"));
	}

	@Transactional
	public List<Cliente> buscarPorNome(String nome) {
		return clienteRepository.findByNomeContainingIgnoreCase(nome);
	}
}