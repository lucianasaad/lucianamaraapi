package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.function.Consumer;
import java.util.function.Function;

public class PessoaLoader<T extends Pessoa> implements ApplicationRunner {

	private final String arquivoCaminho;
	private final Function<String[], T> conversor; // converte linha em objeto T
	private final Consumer<T> serviceInserir;       // serviço para salvar T

	public PessoaLoader(String arquivoCaminho, Function<String[], T> conversor, Consumer<T> serviceInserir) {
		this.arquivoCaminho = arquivoCaminho;
		this.conversor = conversor;
		this.serviceInserir = serviceInserir;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		BufferedReader leitura = new BufferedReader(new FileReader(arquivoCaminho));
		String linha = leitura.readLine();

		while (linha != null) {
			String[] campos = linha.split(";");
			T pessoa = conversor.apply(campos);  // cria objeto a partir da linha
			serviceInserir.accept(pessoa);       // insere no serviço
			System.out.println(pessoa);
			linha = leitura.readLine();
		}

		leitura.close();
	}
}