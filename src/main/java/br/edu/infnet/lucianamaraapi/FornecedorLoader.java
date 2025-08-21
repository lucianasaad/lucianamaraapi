package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import br.edu.infnet.lucianamaraapi.model.service.FornecedorService;
import org.springframework.stereotype.Component;

@Component
public class FornecedorLoader extends PessoaLoader<Fornecedor> {

	public FornecedorLoader(FornecedorService fornecedorService) {
		super(
				"fornecedores.txt", // arquivo
				campos -> { // conversor
					Fornecedor f = new Fornecedor();
					f.setNome(campos[1]);
					f.setTipoPessoa(Pessoa.TipoPessoa.fromCodigo(campos[2]));
					f.setDocumento(campos[3]);
					f.setEmail(campos[4]);
					f.setTelefone(campos[5]);
					f.setCategoria(campos[6]);
					f.setSite(campos[7]);
					f.setContatoResponsavel(campos[8]);

					Endereco end = new Endereco();
					end.setLogradouro(campos[9]);
					end.setNumero(Integer.valueOf(campos[10]));
					end.setLocalidade(campos[11]);
					end.setUf(campos[12]);
					end.setCep(campos[13]);
					f.setEndereco(end);

					return f;
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