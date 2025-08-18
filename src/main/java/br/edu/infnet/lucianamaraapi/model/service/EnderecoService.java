package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EnderecoInvalidoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EnderecoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EnderecoService implements CrudService<Endereco, Integer> {

	private final Map<Integer, Endereco> mapa = new ConcurrentHashMap<>();
	private final AtomicInteger nextId = new AtomicInteger(1);

	private void validar(Endereco endereco) {
		if (endereco == null) {
			throw new IllegalArgumentException("O endereço não pode estar nulo!");
		}

		if (endereco.getCep() == null || endereco.getCep().trim().isEmpty()) {
			throw new EnderecoInvalidoException("O CEP do endereço deve ser informado!");
		}
	}

	@Override
	public Endereco incluir(Endereco endereco) {
		validar(endereco);

		if (endereco.getId() != null && endereco.getId() != 0) {
			throw new IllegalArgumentException("Um novo endereço não pode ter ID na inclusão!");
		}

		endereco.setId(nextId.getAndIncrement());
		mapa.put(endereco.getId(), endereco);

		return endereco;
	}

	@Override
	public Endereco alterar(Integer id, Endereco endereco) {
		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
		}

		validar(endereco);
		buscarPorId(id);

		endereco.setId(id);
		mapa.put(endereco.getId(), endereco);

		return endereco;
	}

	@Override
	public void excluir(Integer id) {
		if (id == null || id == 0) {
			throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
		}

		if (!mapa.containsKey(id)) {
			throw new EnderecoNaoEncontradoException("O Endereço com ID " + id + " não foi encontrado!");
		}

		mapa.remove(id);
	}

	@Override
	public List<Endereco> listarTodos() {
		return new ArrayList<>(mapa.values());
	}

	@Override
	public Endereco buscarPorId(Integer id) {
		Endereco endereco = mapa.get(id);

		if (endereco == null) {
			throw new EnderecoNaoEncontradoException("Endereço ID " + id + " não encontrado!");
		}

		return endereco;
	}
}
