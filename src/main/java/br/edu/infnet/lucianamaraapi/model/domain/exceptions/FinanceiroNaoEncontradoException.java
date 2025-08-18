package br.edu.infnet.lucianamaraapi.model.domain.exceptions;

public class FinanceiroNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FinanceiroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
