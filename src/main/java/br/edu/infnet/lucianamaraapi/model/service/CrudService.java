package br.edu.infnet.lucianamaraapi.model.service;

import java.util.List;

public interface CrudService<T,ID> {

	T incluir(T entity);
	T alterar(ID id, T entity);
	T buscarPorId(ID id);
	void excluir(ID id);
	List<T> listarTodos();
}
