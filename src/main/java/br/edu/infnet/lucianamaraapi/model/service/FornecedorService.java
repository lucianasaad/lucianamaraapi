package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FornecedorInvalidoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FornecedorNaoEncontradoException;
import br.edu.infnet.lucianamaraapi.model.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService implements CrudService<Fornecedor, Integer> {

	private final FornecedorRepository fornecedorRepository;

	public FornecedorService(FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}

	private void validar(Fornecedor fornecedor) {
		if (fornecedor == null) {
			throw new IllegalArgumentException("O fornecedor não pode ser nulo!");
		}
		if (fornecedor.getNome() == null || fornecedor.getNome().trim().isEmpty()) {
			throw new FornecedorInvalidoException("O nome do fornecedor deve ser informado!");
		}
	}

	@Override
	@Transactional
	public Fornecedor incluir(Fornecedor fornecedor) {
		validar(fornecedor);
		if (fornecedor.getId() != null && fornecedor.getId() != 0) {
			throw new IllegalArgumentException("Um novo Fornecedor não pode ter ID definido!");
		}
		return fornecedorRepository.save(fornecedor);
	}

	@Override
	@Transactional
	public Fornecedor alterar(Integer id, Fornecedor fornecedor) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo ou zero!");
		}
		validar(fornecedor);
		buscarPorId(id);
		fornecedor.setId(id);
		return fornecedorRepository.save(fornecedor);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		Fornecedor fornecedor = buscarPorId(id);
		fornecedorRepository.delete(fornecedor);
	}

	@Override
	public List<Fornecedor> listarTodos() {
		return fornecedorRepository.findAll();
	}

	@Override
	public Fornecedor buscarPorId(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("O ID não pode ser nulo/zero!");
		}
		return fornecedorRepository.findById(id)
				.orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor com ID " + id + " não encontrado!"));
	}

	@Transactional
	public Fornecedor atualizarSaldoDevedor(Fornecedor fornecedor, double valor) {
		if (fornecedor == null) {
			throw new IllegalArgumentException("O fornecedor não pode ser nulo!");
		}

		double saldoAtual = fornecedor.getSaldoDevedor() != 0 ? fornecedor.getSaldoDevedor() : 0.0;
		fornecedor.setSaldoDevedor(saldoAtual + valor);
		return fornecedorRepository.save(fornecedor);
	}

	@Transactional
	public Fornecedor buscarPorDocumento(String documento) {
		return fornecedorRepository.findByDocumento(documento)
				.orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor com documento " + documento + " não encontrado!"));
	}

}