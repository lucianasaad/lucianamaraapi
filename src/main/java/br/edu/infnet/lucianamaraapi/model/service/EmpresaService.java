package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EmpresaInvalidaException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EmpresaNaoEncontradaException;
import br.edu.infnet.lucianamaraapi.model.repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService implements CrudService<Empresa, Integer> {

	private final EmpresaRepository empresaRepository;

	public EmpresaService(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}

	private void validar(Empresa empresa) {
		if (empresa == null) {
			throw new IllegalArgumentException("A empresa não pode estar nula!");
		}
		if (empresa.getNome() == null || empresa.getNome().trim().isEmpty()) {
			throw new EmpresaInvalidaException("O nome da empresa deve ser informado!");
		}
	}

	@Override
	@Transactional
	public Empresa incluir(Empresa empresa) {
		validar(empresa);
		if (empresa.getId() != null && empresa.getId() != 0) {
			throw new IllegalArgumentException("Uma nova empresa não pode ter ID definido!");
		}
		return empresaRepository.save(empresa);
	}

	@Override
	@Transactional
	public Empresa alterar(Integer id, Empresa empresa) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("ID inválido para alteração!");
		}
		validar(empresa);
		buscarPorId(id);
		empresa.setId(id);
		return empresaRepository.save(empresa);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		Empresa empresa = buscarPorId(id);
		empresaRepository.delete(empresa);
	}

	@Override
	public List<Empresa> listarTodos() {
		return empresaRepository.findAll();
	}

	@Override
	public Empresa buscarPorId(Integer id) {
		return empresaRepository.findById(id)
				.orElseThrow(() -> new EmpresaNaoEncontradaException("Empresa com ID " + id + " não encontrada!"));
	}

	public Empresa buscarPorDocumento(String documento) {
		return empresaRepository.findByDocumento(documento)
				.orElseThrow(() -> new EmpresaNaoEncontradaException("Empresa com documento " + documento + " não encontrada!"));
	}
}
