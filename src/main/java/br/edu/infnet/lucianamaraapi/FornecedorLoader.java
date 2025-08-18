package br.edu.infnet.lucianamaraapi;

import br.edu.infnet.lucianamaraapi.model.domain.Endereco;
import br.edu.infnet.lucianamaraapi.model.domain.Fornecedor;
import br.edu.infnet.lucianamaraapi.model.domain.Pessoa;
import br.edu.infnet.lucianamaraapi.model.service.FornecedorService;
import org.springframework.stereotype.Component;

@Component
public class FornecedorLoader extends PessoaLoader<Fornecedor> {

	public FornecedorLoader(FornecedorService fornecedorService) {
		super("fornecedores.txt",
				campos -> {
					Fornecedor f = new Fornecedor();
//					f.setId(Integer.parseInt(campos[0]));
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
				fornecedorService::incluir);
	}
}