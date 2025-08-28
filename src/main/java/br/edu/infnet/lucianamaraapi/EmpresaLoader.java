package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Empresa;
import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import br.edu.infnet.lucianamaraapi.model.service.EmpresaService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class EmpresaLoader extends PessoaLoader<Empresa> {

	public EmpresaLoader(EmpresaService empresaService) {
		super(
				"empresas.txt", // arquivo
				campos -> { // conversor
					Empresa empresa = new Empresa();
					empresa.setNome(campos[1]);
					empresa.setRazaoSocial(campos[2]);
					empresa.setNomeFantasia(campos[3]);
					empresa.setTipoPessoa(Pessoa.TipoPessoa.valueOf(campos[4].trim().toUpperCase()));
					empresa.setDocumento(campos[5]);
					empresa.setEmail(campos[6]);
					empresa.setTelefone(campos[7]);

					Endereco end = new Endereco();
					end.setLogradouro(campos[8]);
					end.setNumero(Integer.valueOf(campos[9]));
					end.setBairro(campos[10]);
					end.setLocalidade(campos[11]);
					end.setUf(campos[12]);
					end.setEstado(campos[13]);
					end.setCep(campos[14]);
					empresa.setEndereco(end);

					return empresa;
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
