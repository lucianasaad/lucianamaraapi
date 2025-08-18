package br.edu.infnet.lucianamaraapi.model.domain.exceptions;

public class FornecedorInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FornecedorInvalidoException(String mensagem) {
		super(mensagem);
	}
}
