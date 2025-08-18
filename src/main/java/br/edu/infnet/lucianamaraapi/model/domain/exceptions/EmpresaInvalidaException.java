package br.edu.infnet.lucianamaraapi.model.domain.exceptions;

public class EmpresaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmpresaInvalidaException(String mensagem) {
		super(mensagem);
	}
}
