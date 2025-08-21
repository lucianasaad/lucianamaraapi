package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.PessoaLoader;
import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import br.edu.infnet.lucianamaraapi.model.service.EmpresaService;
import org.springframework.stereotype.Component;

@Component
public class EmpresaLoader extends PessoaLoader<Empresa> {

	public EmpresaLoader(EmpresaService empresaService) {
		super(
				"empresas.txt", // arquivo
				campos -> { // conversor
					Empresa e = new Empresa();
					e.setNome(campos[1]);
					e.setRazaoSocial(campos[2]);
					e.setNomeFantasia(campos[3]);
					e.setTipoPessoa(Pessoa.TipoPessoa.fromCodigo(campos[4]));
					e.setDocumento(campos[5]);
					e.setEmail(campos[6]);
					e.setTelefone(campos[7]);

					Endereco end = new Endereco();
					end.setLogradouro(campos[8]);
					end.setNumero(Integer.valueOf(campos[9]));
					end.setLocalidade(campos[10]);
					end.setUf(campos[11]);
					end.setEstado(campos[12]);
					end.setCep(campos[13]);
					e.setEndereco(end);

					return e;
				},
				empresa -> { // serviceInserir com try/catch
					try {
						empresaService.incluir(empresa);
						System.out.println("[OK] Empresa " + empresa.getNome() + " incluída com sucesso.");
					} catch (Exception ex) {
						System.err.println("[ERRO] Problema na inclusão da empresa: "
								+ empresa.getNome() + " -> " + ex.getMessage());
					}
				},
				() -> System.out.println("[EmpresaLoader] Iniciando carregamento de empresas..."), // preCarregamento
				empresas -> { // posCarregamento
					System.out.println("[EmpresaLoader] Carregamento concluído.");
					System.out.println("--- Empresas Carregadas ---");
					empresas.forEach(System.out::println);
					System.out.println("---------------------------");
				}
		);
	}
}
