package br.edu.infnet.lucianamaraapi.model.domain.exceptions;

public class ClienteInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteInvalidoException(String mensagem) {
		super(mensagem);
	}
}
