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
import java.time.format.DateTimeFormatter;

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
		
		String linha = leitura.readLine();

		String[] campos = null;
		
		while(linha != null) {
			
			campos = linha.split(";");

			Financeiro financeiro = new Financeiro();

			Empresa empresa = new Empresa();
			empresa.setId(Integer.valueOf(campos[0]));
			empresa.setNome(campos[1]);
		
			if (Integer.valueOf(campos[5]) < 0) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(Integer.valueOf(campos[3]));
				fornecedor.setNome(campos[4]);
				financeiro.setPessoaRelacionada(fornecedor);

			} else {
				Cliente cliente = new Cliente();
				cliente.setId(Integer.valueOf(campos[3]));
				cliente.setNome(campos[4]);
				financeiro.setPessoaRelacionada(cliente);
			}

			financeiro.setEmpresa(empresa);
			financeiro.setNatureza(campos[2]);
			financeiro.setTipoFinanceiro(Financeiro.TipoFinanceiro.fromCodigo(Integer.parseInt(campos[5])));
			financeiro.setValor(Double.valueOf(campos[6]));
			financeiro.setDataLancamento(LocalDate.parse(campos[7]));
			financeiro.setDataVencimento(LocalDate.parse(campos[8]));
			if (campos[9] != null && !campos[9].isEmpty()) {
				financeiro.setDataBaixa(LocalDate.parse(campos[9]));
			} else {
				financeiro.setDataBaixa(null);
			}
			financeiro.setStatus(Financeiro.StatusFinanceiro.fromCodigo(campos[10].charAt(0)));

			financeiroService.incluir(financeiro);

			//TODO Listar Financeiros após a leitura do arquivo
			System.out.println(financeiro);
			
			linha = leitura.readLine();
		}
		
		//TODO chamada da funcionalidade de alteração
		
		System.out.println("- " + financeiroService.listarTodos().size());

		leitura.close();
	}
}
