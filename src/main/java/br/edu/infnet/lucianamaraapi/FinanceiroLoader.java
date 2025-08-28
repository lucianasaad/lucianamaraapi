package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.ClienteNaoEncontradoException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.EmpresaNaoEncontradaException;
import br.edu.infnet.lucianamaraapi.model.domain.exceptions.FornecedorNaoEncontradoException;
import br.edu.infnet.lucianamaraapi.model.service.ClienteService;
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

	public FinanceiroLoader(
			FinanceiroService financeiroService,
			EmpresaService empresaService,
			ClienteService clienteService,
			FornecedorService fornecedorService)  {
		this.financeiroService = financeiroService;
		this.empresaService = empresaService;
		this.clienteService = clienteService;
		this.fornecedorService = fornecedorService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		FileReader arquivo = new FileReader("financeiro.txt");
		BufferedReader leitura = new BufferedReader(arquivo);

		System.out.println("[FinanceiroLoader] Iniciando carregamento de financeiros ...");

		String linha = leitura.readLine();

		while (linha != null) {

			String[] campos = linha.split(";");

			if (campos.length < 9) {
				System.err.println("[ERRO] Linha inválida ou incompleta: " + linha);
				linha = leitura.readLine();
				continue;
			}

			Financeiro financeiro = new Financeiro();

			try {
				Empresa empresa = empresaService.buscarPorDocumento(campos[0]);
				financeiro.setEmpresa(empresa);
			} catch (EmpresaNaoEncontradaException e) {
				System.err.println("[ERRO] " + e.getMessage() + " - linha será ignorada.");
				linha = leitura.readLine();
				continue;
			}

			String documentoPessoa = campos[8]; // Cliente ou Fornecedor (BUSCAR PELO DOCUMENTO)
			int tipoFinanceiroCodigo = Integer.parseInt(campos[2]); // TipoFinanceiro
			if (tipoFinanceiroCodigo < 0) {
				try {
					Fornecedor fornecedor = fornecedorService.buscarPorDocumento(documentoPessoa);
					financeiro.setFornecedor(fornecedor);
				} catch (FornecedorNaoEncontradoException e) {
					System.err.println("[ERRO] " + e.getMessage() + " - linha será ignorada.");
					linha = leitura.readLine();
					continue; // pula para a próxima linha
				}
			} else {
				try {
					Cliente cliente = clienteService.buscarPorDocumento(documentoPessoa);
					financeiro.setCliente(cliente);
				} catch (ClienteNaoEncontradoException e) {
					System.err.println("[ERRO] " + e.getMessage() + " - linha será ignorada.");
					linha = leitura.readLine();
					continue;
				}
			}

			financeiro.setNatureza(campos[1]); // Natureza
			financeiro.setTipoFinanceiro(Financeiro.TipoFinanceiro.fromCodigo(tipoFinanceiroCodigo));
			financeiro.setValor(Double.parseDouble(campos[3])); // Valor
			// Datas
			financeiro.setDataLancamento(LocalDate.parse(campos[4]));
			financeiro.setDataVencimento(LocalDate.parse(campos[5]));
			if (campos[6] != null && !campos[6].isBlank()) {
				financeiro.setDataBaixa(LocalDate.parse(campos[6]));
			}
			financeiro.setStatus(Financeiro.StatusFinanceiro.fromCodigo(campos[7].charAt(0))); // Status

			try {
				financeiroService.incluir(financeiro);
				System.out.println("  [OK] Financeiro " + financeiro.getNatureza() + " incluído com sucesso.");
			} catch (Exception e) {
				System.err.println("  [ERRO] Problema na inclusão do financeiro " + financeiro.getNatureza() + ": " + e.getMessage());
			}

			linha = leitura.readLine();
		}

		System.out.println("[FinanceiroLoader] Carregamento concluído.");
		System.out.println("--- Financeiros Carregados ---");
		financeiroService.listarTodos().forEach(System.out::println);
		System.out.println("-----------------------------");

		leitura.close();

	}

}