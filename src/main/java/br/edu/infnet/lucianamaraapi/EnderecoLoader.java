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
		FileReader arquivo = new FileReader("enderecos.txt");
		BufferedReader leitura = new BufferedReader(arquivo);

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

			enderecoService.incluir(endereco);

			System.out.println(endereco);

			linha = leitura.readLine();
		}

		leitura.close();
	}
}
