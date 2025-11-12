package br.edu.infnet.lucianamaraapi.model.service;

import br.edu.infnet.lucianamaraapi.model.domain.Banco;
import br.edu.infnet.lucianamaraapi.model.domain.ContaBancaria;
import br.edu.infnet.lucianamaraapi.model.repository.ContaBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaBancariaService implements CrudService<ContaBancaria, Integer> {

    private final ContaBancariaRepository contaBancariaRepository;
    private final BancoService bancoService;

    public ContaBancariaService(ContaBancariaRepository contaBancariaRepository,
                                BancoService bancoService) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.bancoService = bancoService;
    }

    @Override
    @Transactional
    public ContaBancaria incluir(ContaBancaria contaBancaria) {
        return contaBancariaRepository.save(contaBancaria);
    }

    @Override
    @Transactional
    public ContaBancaria alterar(Integer id, ContaBancaria contaBancaria) {
        buscarPorId(id);
        contaBancaria.setId(id);
        return contaBancariaRepository.save(contaBancaria);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        ContaBancaria conta = buscarPorId(id);
        contaBancariaRepository.delete(conta);
    }

    @Override
    public List<ContaBancaria> listarTodos() {
        return contaBancariaRepository.findAll();
    }

    @Override
    public ContaBancaria buscarPorId(Integer id) {
        return contaBancariaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta bancária com ID " + id + " não encontrada!"));
    }

    public ContaBancaria buscarPorBancoAgenciaNumero(Integer bancoId, String agencia, String numeroConta) {
        return contaBancariaRepository
                .findByBancoIdAndAgenciaAndNumeroConta(bancoId, agencia, numeroConta)
                .orElse(null);
    }

    /**
     * Resolve o banco por nome OU código, e garante unicidade por (banco + agência + número).
     */
    @Transactional
    public ContaBancaria buscarOuCriar(String bancoNomeOuCodigo, String agencia, String numeroConta) {
        if (agencia == null || agencia.isBlank()) {
            throw new IllegalArgumentException("Agência vazia/inválida.");
        }
        if (numeroConta == null || numeroConta.isBlank()) {
            throw new IllegalArgumentException("Número da conta vazio/inválido.");
        }

        Banco banco = bancoService.buscarOuCriarPorNomeOuCodigo(bancoNomeOuCodigo);

        ContaBancaria existente = buscarPorBancoAgenciaNumero(banco.getId(), agencia, numeroConta);
        if (existente != null) {
            return existente;
        }

        ContaBancaria nova = new ContaBancaria();
        nova.setBanco(banco);
        nova.setAgencia(agencia);
        nova.setNumeroConta(numeroConta);
        // opcional: definir tipo aqui se desejar (ex.: "Corrente")
        return contaBancariaRepository.save(nova);
    }
}
