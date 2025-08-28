package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import br.edu.infnet.lucianamaraapi.model.service.FornecedorService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class FornecedorLoader extends PessoaLoader<Fornecedor> {

	public FornecedorLoader(FornecedorService fornecedorService) {
		super(
				"fornecedores.txt", // arquivo
				campos -> { // conversor
					Fornecedor fornecedor = new Fornecedor();
					fornecedor.setNome(campos[1]);
					fornecedor.setTipoPessoa(Pessoa.TipoPessoa.valueOf(campos[2].trim().toUpperCase()));
					fornecedor.setDocumento(campos[3]);
					fornecedor.setEmail(campos[4]);
					fornecedor.setTelefone(campos[5]);
					fornecedor.setCategoria(campos[6]);
					fornecedor.setSite(campos[7]);
					fornecedor.setContatoResponsavel(campos[8]);

					Endereco endereco = new Endereco();
					endereco.setLogradouro(campos[9]);
					endereco.setNumero(Integer.valueOf(campos[10]));
					endereco.setLocalidade(campos[11]);
					endereco.setUf(campos[12]);
					endereco.setCep(campos[13]);
					fornecedor.setEndereco(endereco);

					return fornecedor;
				},
				f -> { // serviceInserir com try/catch
					try {
						fornecedorService.incluir(f);
						System.out.println("  [OK] Fornecedor " + f.getNome() + " incluído com sucesso.");
					} catch (Exception e) {
						System.err.println("[ERRO] Problema na inclusão do fornecedor: "
								+ f.getNome() + ": " + e.getMessage());
					}
				},
				() -> System.out.println("[FornecedorLoader] Iniciando carregamento de fornecedores..."), // preCarregamento
				fornecedores -> { // posCarregamento
					System.out.println("[FornecedorLoader] Carregamento concluído.");
					System.out.println("--- Fornecedores Carregados ---");
					fornecedores.forEach(System.out::println);
					System.out.println("-------------------------------");
				}
		);
	}
}