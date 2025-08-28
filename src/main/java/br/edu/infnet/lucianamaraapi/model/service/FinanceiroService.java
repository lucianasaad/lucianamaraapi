package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FinanceiroInvalidoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FinanceiroNaoEncontradoException;
import br.edu.infnet.lucianamaraapi.model.repository.FinanceiroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinanceiroService implements CrudService<Financeiro, Integer> {


	private final FinanceiroRepository financeiroRepository;
	private final ClienteService clienteService;
	private final FornecedorService fornecedorService;

	public FinanceiroService(FinanceiroRepository financeiroRepository,
							 ClienteService clienteService,
							 FornecedorService fornecedorService) {
		this.financeiroRepository = financeiroRepository;
		this.clienteService = clienteService;
		this.fornecedorService = fornecedorService;
	}

	private void validar(Financeiro financeiro) {
		if (financeiro == null) {
			throw new IllegalArgumentException("O Financeiro não pode estar nulo!");
		}
		if (financeiro.getValor() <= 0) {
			throw new FinanceiroInvalidoException("O valor do financeiro é obrigatório e deve ser maior que zero!");
		}
		if (financeiro.getEmpresa() == null) {
			throw new FinanceiroInvalidoException("A empresa deve ser informada no lançamento!");
		}
		if (financeiro.getTipoFinanceiro() == null) {
			throw new FinanceiroInvalidoException("O tipo de financeiro deve ser informado!");
		}

		// se for RECEBER precisa de cliente; se PAGAR precisa de fornecedor
		if (financeiro.getTipoFinanceiro() == Financeiro.TipoFinanceiro.RECEBER && financeiro.getCliente() == null) {
			throw new FinanceiroInvalidoException("Lançamento RECEBER precisa ter um Cliente vinculado!");
		}
		if (financeiro.getTipoFinanceiro() == Financeiro.TipoFinanceiro.PAGAR && financeiro.getFornecedor() == null) {
			throw new FinanceiroInvalidoException("Lançamento PAGAR precisa ter um Fornecedor vinculado!");
		}
	}

	@Override
	@Transactional
	public Financeiro incluir(Financeiro financeiro) {
		validar(financeiro);
		atualizarSaldosAoIncluir(financeiro);
		return financeiroRepository.save(financeiro);
	}

	@Override
	@Transactional
	public Financeiro alterar(Integer id, Financeiro financeiro) {

		validar(financeiro);
		Financeiro finAtual = buscarPorId(financeiro.getId());

		if (finAtual.isAberto() && financeiro.isBaixado()) {
			atualizarSaldosAoBaixar(financeiro);
		}

		return financeiroRepository.save(financeiro);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		Financeiro financeiro = buscarPorId(id);

		if (financeiro.isAberto()) {
			reverterSaldosAoExcluir(financeiro);
		}

		financeiroRepository.deleteById(id);
	}

	@Override
	public List<Financeiro> listarTodos() {
		return financeiroRepository.findAll();
	}

	@Override
	public Financeiro buscarPorId(Integer id) {
		return financeiroRepository.findById(id)
				.orElseThrow(() -> new FinanceiroNaoEncontradoException("Financeiro com ID " + id + " não encontrado!"));
	}

	@Transactional
	public Financeiro baixar(Integer id) {
		Financeiro financeiro = buscarPorId(id);

		// se não está em aberto, nada a fazer (ou lançar exceção se preferir)
		if (!financeiro.isFinanceiroEmAberto()) {
			throw new FinanceiroInvalidoException("Apenas títulos em aberto podem ser baixados.");
   	    }

		financeiro.baixarFinanceiro();
		Financeiro finAtual = financeiroRepository.save(financeiro);
		atualizarSaldosAoBaixar(finAtual);

		return finAtual;
	}

	@Transactional
	public Financeiro baixarParcial(Integer id, double valorPago) {
		if (valorPago <= 0) {
			throw new FinanceiroInvalidoException("O valor pago deve ser maior que zero!");
		}

		Financeiro financeiro = buscarPorId(id);

		if (!financeiro.isFinanceiroEmAberto()) {
			throw new FinanceiroInvalidoException("Apenas títulos em aberto podem ser baixados.");
		}

		double valorAtual = financeiro.getValor();

		if (valorPago >= valorAtual) {
			return baixar(id);
		}

		financeiro.setValor(valorAtual - valorPago);
		Financeiro finAtual = financeiroRepository.save(financeiro);

		if (finAtual.isRecebimento() && finAtual.getCliente() != null) {
			clienteService.atualizarSaldoReceber(finAtual.getCliente(), -valorPago);
		} else if (finAtual.isPagamento() && finAtual.getFornecedor() != null) {
			fornecedorService.atualizarSaldoDevedor(finAtual.getFornecedor(), -valorPago);
		}

		return finAtual;
	}

	@Transactional
	public List<Financeiro> buscarPorDataVencimento(LocalDate inicio, LocalDate fim) {
		if (inicio != null && fim != null) {
			return financeiroRepository.findByDataVencimentoBetween(inicio, fim);
		} else if (inicio != null) {
			return financeiroRepository.findByDataVencimentoAfter(inicio);
		} else if (fim != null) {
			return financeiroRepository.findByDataVencimentoBefore(fim);
		} else {
			return listarTodos();
		}
	}

	private void atualizarSaldosAoIncluir(Financeiro financeiro) {
		if (financeiro.isRecebimento() && financeiro.getCliente() != null) {
			clienteService.atualizarSaldoReceber(financeiro.getCliente(), financeiro.getValor());
		} else if (financeiro.isPagamento() && financeiro.getFornecedor() != null) {
			fornecedorService.atualizarSaldoDevedor(financeiro.getFornecedor(), financeiro.getValor());
		}
	}

	private void atualizarSaldosAoBaixar(Financeiro financeiro) {
		if (financeiro.isRecebimento() && financeiro.getCliente() != null) {
			clienteService.atualizarSaldoReceber(financeiro.getCliente(), -financeiro.getValor());
		} else if (financeiro.isPagamento() && financeiro.getFornecedor() != null) {
			fornecedorService.atualizarSaldoDevedor(financeiro.getFornecedor(), -financeiro.getValor());
		}
	}

	private void reverterSaldosAoExcluir(Financeiro financeiro) {
		if (financeiro.isRecebimento() && financeiro.getCliente() != null) {
			clienteService.atualizarSaldoReceber(financeiro.getCliente(), -financeiro.getValor());
		} else if (financeiro.isPagamento() && financeiro.getFornecedor() != null) {
			fornecedorService.atualizarSaldoDevedor(financeiro.getFornecedor(), -financeiro.getValor());
		}
	}

}