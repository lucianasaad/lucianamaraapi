package br.edu.infnet.lucianamaraapi.model.domain.exceptions;

public class EmpresaNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmpresaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
}
