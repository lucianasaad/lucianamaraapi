package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.ContaBancaria;
import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.ClienteNaoEncontradoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EmpresaNaoEncontradaException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FornecedorNaoEncontradoException;
import br.edu.infnet.lucianamaraapi.model.service.ClienteService;
import br.edu.infnet.lucianamaraapi.model.service.ContaBancariaService;
import br.edu.infnet.lucianamaraapi.model.service.EmpresaService;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;
import br.edu.infnet.lucianamaraapi.model.service.FornecedorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

@Component
public class FinanceiroLoader implements ApplicationRunner {

	private final FinanceiroService financeiroService;
	private final EmpresaService empresaService;
	private final ClienteService clienteService;
	private final FornecedorService fornecedorService;
	private final ContaBancariaService contaBancariaService;

	public FinanceiroLoader(
			FinanceiroService financeiroService,
			EmpresaService empresaService,
			ClienteService clienteService,
			FornecedorService fornecedorService,
			ContaBancariaService contaBancariaService
	) {
		this.financeiroService = financeiroService;
		this.empresaService = empresaService;
		this.clienteService = clienteService;
		this.fornecedorService = fornecedorService;
		this.contaBancariaService = contaBancariaService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		System.out.println("[FinanceiroLoader] Iniciando carregamento de financeiros ...");

		try (BufferedReader leitura = new BufferedReader(new FileReader("financeiro.txt"))) {

			String linha;
			while ((linha = leitura.readLine()) != null) {

				// ignora linhas em branco e comentários
				if (linha.isBlank() || linha.trim().startsWith("#")) {
					continue;
				}

				String[] campos = linha.split(";");
				if (campos.length < 12) {
					System.err.println("[ERRO] Linha inválida/incompleta (" + campos.length + " campos): " + linha);
					continue;
				}

				Financeiro financeiro = new Financeiro();

				// 1) Empresa (doc)
				try {
					Empresa empresa = empresaService.buscarPorDocumento(campos[0]);
					financeiro.setEmpresa(empresa);
				} catch (EmpresaNaoEncontradaException e) {
					System.err.println("[ERRO] " + e.getMessage() + " - linha ignorada.");
					continue;
				}

				// 2) Natureza
				financeiro.setNatureza(campos[1]);

				// 3) Tipo financeiro (1 = RECEBER, -1 = PAGAR)
				int tipoFinanceiroCodigo;
				try {
					tipoFinanceiroCodigo = Integer.parseInt(campos[2]);
					financeiro.setTipoFinanceiro(Financeiro.TipoFinanceiro.fromCodigo(tipoFinanceiroCodigo));
				} catch (NumberFormatException ex) {
					System.err.println("[ERRO] TipoFinanceiro inválido: '" + campos[2] + "' - linha ignorada.");
					continue;
				}

				// 4) Valor
				try {
					financeiro.setValor(Double.parseDouble(campos[3]));
				} catch (NumberFormatException ex) {
					System.err.println("[ERRO] Valor inválido: '" + campos[3] + "' - linha ignorada.");
					continue;
				}

				// 5) Data lançamento
				try {
					financeiro.setDataLancamento(LocalDate.parse(campos[4]));
				} catch (Exception ex) {
					System.err.println("[ERRO] Data de lançamento inválida: '" + campos[4] + "' - linha ignorada.");
					continue;
				}

				// 6) Data vencimento
				try {
					financeiro.setDataVencimento(LocalDate.parse(campos[5]));
				} catch (Exception ex) {
					System.err.println("[ERRO] Data de vencimento inválida: '" + campos[5] + "' - linha ignorada.");
					continue;
				}

				// 7) Data baixa (opcional)
				if (campos[6] != null && !campos[6].isBlank()) {
					try {
						financeiro.setDataBaixa(LocalDate.parse(campos[6]));
					} catch (Exception ex) {
						System.err.println("[ERRO] Data de baixa inválida: '" + campos[6] + "' - será ignorada.");
					}
				}

				// 8) Status (A/B)
				try {
					financeiro.setStatus(Financeiro.StatusFinanceiro.fromCodigo(campos[7].charAt(0)));
				} catch (Exception ex) {
					System.err.println("[ERRO] Status inválido: '" + campos[7] + "' - linha ignorada.");
					continue;
				}

				// 9) Documento Cliente/Fornecedor + definição de pessoa
				String documentoPessoa = campos[8];
				if (tipoFinanceiroCodigo < 0) {
					// PAGAR → Fornecedor
					try {
						Fornecedor fornecedor = fornecedorService.buscarPorDocumento(documentoPessoa);
						financeiro.setFornecedor(fornecedor);
					} catch (FornecedorNaoEncontradoException e) {
						System.err.println("[ERRO] " + e.getMessage() + " - linha ignorada.");
						continue;
					}
				} else {
					// RECEBER → Cliente
					try {
						Cliente cliente = clienteService.buscarPorDocumento(documentoPessoa);
						financeiro.setCliente(cliente);
					} catch (ClienteNaoEncontradoException e) {
						System.err.println("[ERRO] " + e.getMessage() + " - linha ignorada.");
						continue;
					}
				}

				// 10) Agência
				String agencia = campos[9].trim();
				if (agencia.isEmpty()) {
					System.err.println("[ERRO] Agência vazia - linha ignorada.");
					continue;
				}

				// 11) Número da conta
				String numeroConta = campos[10].trim();
				if (numeroConta.isEmpty()) {
					System.err.println("[ERRO] Número da conta vazio - linha ignorada.");
					continue;
				}

				// 12) Banco (nome ou código). Se vier vazio, usa um padrão ("Itaú")
				String bancoNomeOuCodigo = (campos[11] != null && !campos[11].isBlank())
						? campos[11].trim()
						: "Itaú";

				// ✅ Conta bancária única por (banco + agência + número)
				// OBS.: garanta no ContaBancariaService um método:
				// ContaBancaria buscarOuCriar(String bancoNomeOuCodigo, String agencia, String numeroConta)
				ContaBancaria conta = contaBancariaService.buscarOuCriar(bancoNomeOuCodigo, agencia, numeroConta);
				financeiro.setContaBancaria(conta);

				// Persistência
				try {
					financeiroService.incluir(financeiro);
					System.out.println("  [OK] " + financeiro.getNatureza() + " incluído com sucesso.");
				} catch (Exception e) {
					System.err.println("  [ERRO] Inclusão falhou para '" + financeiro.getNatureza() + "': " + e.getMessage());
				}
			}

			System.out.println("[FinanceiroLoader] Carregamento concluído.");
			System.out.println("--- Financeiros Carregados ---");
			financeiroService.listarTodos().forEach(System.out::println);
			System.out.println("------------------------------");
		}
	}
}
