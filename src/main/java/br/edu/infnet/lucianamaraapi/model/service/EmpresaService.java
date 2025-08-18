package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EmpresaInvalidaException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EmpresaNaoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmpresaService implements CrudService<Empresa, Integer> {

	private final Map<Integer, Empresa> mapa = new ConcurrentHashMap<Integer, Empresa>();
	private final AtomicInteger nextId = new AtomicInteger(1);

	private void validar(Empresa empresa) {
		if(empresa == null) {
			throw new IllegalArgumentException("A empresa não pode estar nula!");
		}

		if(empresa.getNome() == null || empresa.getNome().trim().isEmpty()) {
			throw new EmpresaInvalidaException("O nome da empresa deve ser informado!");
		}
	}

	@Override
	public Empresa incluir(Empresa empresa) {

		validar(empresa);

		if(empresa.getId() != null && empresa.getId() != 0) {
			throw new IllegalArgumentException("Uma nova Empresa não pode ter um ID na inclusão!");
		}

		empresa.setId(nextId.getAndIncrement());

		mapa.put(empresa.getId(), empresa);

		return empresa;
	}

	@Override
	public Empresa alterar(Integer id, Empresa empresa) {

		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
		}

		validar(empresa);

		buscarPorId(id);

		empresa.setId(id);

		mapa.put(empresa.getId(), empresa);

		return empresa;
	}

	@Override
	public void excluir(Integer id) {
		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");
		}

		if(!mapa.containsKey(id)) {
			throw new EmpresaNaoEncontradaException("A Empresa com ID " + id + " não foi encontrada!");
		}

		mapa.remove(id);
	}


	@Override
	public List<Empresa> listarTodos() {

		return new ArrayList<Empresa>(mapa.values());
	}

	@Override
	public Empresa buscarPorId(Integer id) {

		Empresa empresa = mapa.get(id);

		if(empresa == null) {
			throw new IllegalArgumentException("Empresa ID " + id + " não encontrado!");
		}

		return empresa;
	}
}
