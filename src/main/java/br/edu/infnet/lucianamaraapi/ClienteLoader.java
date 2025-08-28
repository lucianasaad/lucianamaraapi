package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Cliente;
import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import br.edu.infnet.lucianamaraapi.model.service.ClienteService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(1)
public class ClienteLoader extends PessoaLoader<Cliente> {

	public ClienteLoader(ClienteService clienteService) {
		super(
				"clientes.txt", // arquivo
				campos -> { // conversor
					Cliente cliente = new Cliente();
					cliente.setNome(campos[1]);
					cliente.setTipoPessoa(Pessoa.TipoPessoa.valueOf(campos[2].trim().toUpperCase()));
					cliente.setDocumento(campos[3]);
					cliente.setEmail(campos[4]);
					cliente.setTelefone(campos[5]);
					cliente.setMediaAtraso(Integer.parseInt(campos[6]));
					cliente.setSaldoReceber(Double.parseDouble(campos[7]));
					cliente.setDataProxVencimento(LocalDate.parse(campos[8]));
					cliente.setAtivo(Boolean.parseBoolean(campos[9]));

					Endereco end = new Endereco();
					end.setLogradouro(campos[10]);
					end.setNumero(Integer.valueOf(campos[11]));
					end.setLocalidade(campos[12]);
					end.setUf(campos[13]);
					end.setCep(campos[14]);
					cliente.setEnderecoCobranca(end);

					return cliente;
				},
				cliente -> { // serviceInserir
					try {
						clienteService.incluir(cliente);
						System.out.println("  [OK] Cliente " + cliente.getNome() + " incluído com sucesso.");
					} catch (Exception e) {
						System.err.println("[ERRO] Problema na inclusão do cliente: "
								+ cliente.getNome() + ": " + e.getMessage());
					}
				},
				() -> System.out.println("[ClienteLoader] Iniciando carregamento de clientes..."), // preCarregamento
				clientes -> { // posCarregamento
					System.out.println("[ClienteLoader] Carregamento concluído.");
					System.out.println("--- Clientes Carregados ---");
					clientes.forEach(System.out::println);
					System.out.println("---------------------------");
				}
		);
	}
}