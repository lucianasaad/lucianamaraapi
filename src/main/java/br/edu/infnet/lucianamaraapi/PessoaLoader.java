package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class PessoaLoader<T extends Pessoa> implements ApplicationRunner {

	private final String arquivoCaminho;
	private final Function<String[], T> conversor; // converte linha em objeto T
	private final Consumer<T> serviceInserir;       // serviço para salvar T
	private final Runnable preCarregamento;               // ação antes do carregamento
	private final Consumer<List<T>> posCarregamento;      // ação no fim do carregamento

	public PessoaLoader(String arquivoCaminho,
						Function<String[], T> conversor,
						Consumer<T> serviceInserir,
						Runnable preCarregamento,
						Consumer<List<T>> posCarregamento) {
		this.arquivoCaminho = arquivoCaminho;
		this.conversor = conversor;
		this.serviceInserir = serviceInserir;
		this.preCarregamento = preCarregamento;
		this.posCarregamento = posCarregamento;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (preCarregamento != null) {
			preCarregamento.run();
		}

		List<T> carregados = new ArrayList<>();

		try (BufferedReader leitura = new BufferedReader(new FileReader(arquivoCaminho))) {
			String linha = leitura.readLine();

			while (linha != null) {
				String[] campos = linha.split(";");
				T pessoa = conversor.apply(campos);
				serviceInserir.accept(pessoa);
				carregados.add(pessoa);
				linha = leitura.readLine();
			}
			leitura.close();
		}

		if (posCarregamento != null) {
			posCarregamento.accept(carregados);
		}

	}
}