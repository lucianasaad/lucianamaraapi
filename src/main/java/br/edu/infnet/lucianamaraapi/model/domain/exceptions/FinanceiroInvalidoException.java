package br.edu.infnet.lucianamaraapi.model.domain.exceptions;

public class FinanceiroInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FinanceiroInvalidoException(String mensagem) {
		super(mensagem);
	}
}
