package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Banco;
import br.edu.infnet.lucianamaraapi.model.repository.BancoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoService implements CrudService<Banco, Integer> {

    private final BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    @Override
    @Transactional
    public Banco incluir(Banco banco) {
        return bancoRepository.save(banco);
    }

    @Override
    @Transactional
    public Banco alterar(Integer id, Banco banco) {
        buscarPorId(id);
        banco.setId(id);
        return bancoRepository.save(banco);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        Banco banco = buscarPorId(id);
        bancoRepository.delete(banco);
    }

    @Override
    public List<Banco> listarTodos() {
        return bancoRepository.findAll();
    }

    @Override
    public Banco buscarPorId(Integer id) {
        return bancoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banco com ID " + id + " não encontrado!"));
    }

    public Banco buscarPorNome(String nome) {
        return bancoRepository.findByNomeIgnoreCase(nome).orElse(null);
    }

    public Banco buscarPorCodigo(String codigo) {
        return bancoRepository.findByCodigo(codigo).orElse(null);
    }

    /**
     * Tenta resolver por código (se for numérico/curto) e, se não achar, por nome.
     * Se não existir, cria com o valor informado (tentando inferir se é código).
     */
    @Transactional
    public Banco buscarOuCriarPorNomeOuCodigo(String nomeOuCodigo) {
        if (nomeOuCodigo == null || nomeOuCodigo.isBlank()) {
            throw new IllegalArgumentException("Nome/código do banco vazio.");
        }

        // 1) Tenta por código (ex.: "001", "237"), se parecer um código
        if (pareceCodigo(nomeOuCodigo)) {
            Banco porCodigo = buscarPorCodigo(nomeOuCodigo);
            if (porCodigo != null) return porCodigo;
        }

        // 2) Tenta por nome
        Banco porNome = buscarPorNome(nomeOuCodigo);
        if (porNome != null) return porNome;

        // 3) Não achou → cria. Se parece código, salva em codigo; caso contrário, como nome.
        Banco novo = new Banco();
        if (pareceCodigo(nomeOuCodigo)) {
            novo.setCodigo(nomeOuCodigo);
            novo.setNome(nomeOuCodigo); // opcional: pode deixar nome igual ao código até ajustar depois
        } else {
            novo.setNome(nomeOuCodigo);
        }
        return bancoRepository.save(novo);
    }

    private boolean pareceCodigo(String s) {
        // simples heurística: se tem até 5 chars e todos dígitos
        if (s.length() <= 5) {
            for (int i = 0; i < s.length(); i++) {
                if (!Character.isDigit(s.charAt(i))) return false;
            }
            return true;
        }
        return false;
    }
}
