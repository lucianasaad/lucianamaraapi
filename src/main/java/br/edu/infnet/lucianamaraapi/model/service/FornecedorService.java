package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FornecedorInvalidoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FornecedorNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FornecedorService implements CrudService<Fornecedor, Integer> {

	private final Map<Integer, Fornecedor> mapa = new ConcurrentHashMap<>();
	private final AtomicInteger nextId = new AtomicInteger(1);

	private void validar(Fornecedor fornecedor) {
		if (fornecedor == null) {
			throw new IllegalArgumentException("O fornecedor não pode estar nulo!");
		}

		if (fornecedor.getNome() == null || fornecedor.getNome().trim().isEmpty()) {
			throw new FornecedorInvalidoException("O nome do fornecedor deve ser informado!");
		}
	}

	@Override
	public Fornecedor incluir(Fornecedor fornecedor) {

		validar(fornecedor);

		if (fornecedor.getId() != null && fornecedor.getId() != 0) {
			throw new IllegalArgumentException("Um novo Fornecedor não pode ter um ID na inclusão!");
		}

		fornecedor.setId(nextId.getAndIncrement());
		mapa.put(fornecedor.getId(), fornecedor);

		return fornecedor;
	}

	@Override
	public Fornecedor alterar(Integer id, Fornecedor fornecedor) {

		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
		}

		validar(fornecedor);
		buscarPorId(id);

		fornecedor.setId(id);
		mapa.put(fornecedor.getId(), fornecedor);

		return fornecedor;
	}

	@Override
	public void excluir(Integer id) {
		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
		}

		if (!mapa.containsKey(id)) {
			throw new FornecedorNaoEncontradoException("O Fornecedor com ID " + id + " não foi encontrado!");
		}

		mapa.remove(id);
	}

	@Override
	public List<Fornecedor> listarTodos() {
		return new ArrayList<>(mapa.values());
	}

	@Override
	public Fornecedor buscarPorId(Integer id) {
		Fornecedor fornecedor = mapa.get(id);

		if (fornecedor == null) {
			throw new FornecedorNaoEncontradoException("Fornecedor ID " + id + " não encontrado!");
		}

		return fornecedor;
	}

	public Fornecedor atualizarSaldoDevedor(Fornecedor fornecedor, double valor) {

		double saldoAtual = fornecedor.getSaldoDevedor() != 0 ? fornecedor.getSaldoDevedor() : 0.0;
		fornecedor.setSaldoDevedor(saldoAtual + (valor));
		alterar(fornecedor.getId(), fornecedor);
		return fornecedor;
	}

}
