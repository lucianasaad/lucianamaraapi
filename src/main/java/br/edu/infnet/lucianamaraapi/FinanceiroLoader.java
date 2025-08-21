package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.Financeiro;
import br.edu.infnet.lucianamaraapi.model.service.FinanceiroService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

@Component
public class FinanceiroLoader implements ApplicationRunner {

	private final FinanceiroService financeiroService;

	public FinanceiroLoader(FinanceiroService financeiroService) {
		this.financeiroService = financeiroService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		FileReader arquivo = new FileReader("financeiro.txt");
		BufferedReader leitura = new BufferedReader(arquivo);

		System.out.println("[FinanceiroLoader] Iniciando carregamento de financeiros ...");

		String linha = leitura.readLine();

		while (linha != null) {

			String[] campos = linha.split(";");

			Financeiro financeiro = new Financeiro();

			Empresa empresa = new Empresa();
			empresa.setId(Integer.valueOf(campos[0]));
			empresa.setNome(campos[1]);
			financeiro.setEmpresa(empresa);

			financeiro.setNatureza(campos[2]);
			financeiro.setTipoFinanceiro(Financeiro.TipoFinanceiro.fromCodigo(Integer.parseInt(campos[5])));
			financeiro.setValor(Double.valueOf(campos[6]));
			financeiro.setDataLancamento(LocalDate.parse(campos[7]));
			financeiro.setDataVencimento(LocalDate.parse(campos[8]));

			if (Integer.parseInt(campos[5]) < 0) { // Pagamento: Fornecedor
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(Integer.valueOf(campos[3]));
				fornecedor.setNome(campos[4]);
				financeiro.setFornecedor(fornecedor);
			} else { // Recebimento: Cliente
				Cliente cliente = new Cliente();
				cliente.setId(Integer.valueOf(campos[3]));
				cliente.setNome(campos[4]);
				financeiro.setCliente(cliente);
			}

			if (campos[9] != null && !campos[9].isEmpty()) {
				financeiro.setDataBaixa(LocalDate.parse(campos[9]));
			}

			financeiro.setStatus(Financeiro.StatusFinanceiro.fromCodigo(campos[10].charAt(0)));

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
