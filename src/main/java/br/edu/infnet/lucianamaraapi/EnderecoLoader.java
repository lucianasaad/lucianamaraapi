package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.service.EnderecoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class EnderecoLoader implements ApplicationRunner {

	private final EnderecoService enderecoService;

	public EnderecoLoader(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		try (BufferedReader leitura = new BufferedReader(new FileReader("enderecos.txt"))) {

			System.out.println("[EnderecoLoader] Iniciando carregamento de endereços ...");

			String linha = leitura.readLine();

			while (linha != null) {
				String[] campos = linha.split(";");

				Endereco endereco = new Endereco();
				endereco.setCep(campos[0]);
				endereco.setLogradouro(campos[1]);
				endereco.setNumero(Integer.parseInt(campos[2]));
				endereco.setComplemento(campos[3]);
				endereco.setUnidade(campos[4]);
				endereco.setBairro(campos[5]);
				endereco.setLocalidade(campos[6]);
				endereco.setUf(campos[7]);
				endereco.setEstado(campos[8]);

				try {
					enderecoService.incluir(endereco);
					System.out.println("  [OK] Endereço " + endereco.getLogradouro() + " incluído com sucesso.");
				} catch (Exception e) {
					System.err.println("  [ERRO] Problema na inclusão do endereço " + endereco.getLogradouro() + ": " + e.getMessage());
				}

				linha = leitura.readLine();
			}

			System.out.println("[EnderecoLoader] Carregamento concluído.");
			System.out.println("--- Endereços Carregados ---");
			enderecoService.listarTodos().forEach(System.out::println);
			System.out.println("---------------------------");

			leitura	.close();
		}
	}
}