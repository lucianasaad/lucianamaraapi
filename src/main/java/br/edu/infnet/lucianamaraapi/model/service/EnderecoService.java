package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService implements CrudService<Endereco, Integer> {

	private final EnderecoRepository enderecoRepository;

	public EnderecoService(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	@Override
	@Transactional
	public Endereco incluir(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	@Override
	@Transactional
	public Endereco alterar(Integer id, Endereco endereco) {
		buscarPorId(id);
		endereco.setId(id);
		return enderecoRepository.save(endereco);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		Endereco endereco = buscarPorId(id);
		enderecoRepository.delete(endereco);
	}

	@Override
	public List<Endereco> listarTodos() {
		return enderecoRepository.findAll();
	}

	@Override
	public Endereco buscarPorId(Integer id) {
		return enderecoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + id + " não encontrado!"));
	}
}
