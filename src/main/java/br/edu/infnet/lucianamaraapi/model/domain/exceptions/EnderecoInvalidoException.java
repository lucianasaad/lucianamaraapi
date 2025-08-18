package br.edu.infnet.lucianamaraapi.model.domain.exceptions;

public class EnderecoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EnderecoInvalidoException(String mensagem) {
		super(mensagem);
	}
}
