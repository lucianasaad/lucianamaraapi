package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FinanceiroInvalidoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FinanceiroNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FinanceiroService implements CrudService<Financeiro, Integer> {

	private final Map<Integer, Financeiro> mapa = new ConcurrentHashMap<Integer, Financeiro>();
	private final AtomicInteger nextId = new AtomicInteger(1);
	
	private void validar(Financeiro financeiro) {
		if(financeiro == null) {
			throw new IllegalArgumentException("O Financeiro não pode estar nulo!");
		}

		if(financeiro.getValor() <= 0) {
			throw new FinanceiroInvalidoException("O valor do financeiro é obrigatório e deve ser maior que zero!");
		}		
	}
	
	@Override
	public Financeiro incluir(Financeiro financeiro) {
		
		validar(financeiro);
		
		if(financeiro.getId() != null && financeiro.getId() != 0) {
			throw new IllegalArgumentException("Um novo Financeiro não pode ter um ID na inclusão!");			
		}
		
		financeiro.setId(nextId.getAndIncrement());
		
		mapa.put(financeiro.getId(), financeiro);
		
		return financeiro;
	}

	@Override
	public Financeiro alterar(Integer id, Financeiro financeiro) {

		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");			
		}
		
		validar(financeiro);
		
		buscarPorId(id);
		
		financeiro.setId(id);

		mapa.put(financeiro.getId(), financeiro);
		
		return financeiro;
	}

	@Override
	public void excluir(Integer id) {
		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para exclusão não pode ser nulo/zero!");			
		}
		
		if(!mapa.containsKey(id)) {
			throw new FinanceiroNaoEncontradoException("O Financeiro com ID " + id + " não foi encontrado!");
		}

		mapa.remove(id);
	}

	public Financeiro baixar(Integer id) {

		if(id == null || id == 0) {
			throw new IllegalArgumentException("O ID para baixa não pode ser nulo/zero!");
		}
		
		Financeiro financeiro = buscarPorId(id);
		
		if(!financeiro.isFinanceiroEmAberto()) {
			System.out.println("Financeiro " + financeiro.getId() + " já está baixado!");
			return financeiro;
		}
		
		financeiro.baixarFinanceiro();
		
		mapa.put(financeiro.getId(), financeiro);
		
		return financeiro;
	}
	
	@Override
	public List<Financeiro> listarTodos() {
		
		return new ArrayList<Financeiro>(mapa.values());
	}

	@Override
	public Financeiro buscarPorId(Integer id) {

		Financeiro financeiro = mapa.get(id);
		
		if(financeiro == null) {
			throw new IllegalArgumentException("Financeiro ID " + id + " não encontrado!");
		}
		
		return financeiro;
	}
} 