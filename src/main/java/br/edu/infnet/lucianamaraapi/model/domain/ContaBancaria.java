package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Objects;

@Entity
@Table(
        name = "conta_bancaria",
        uniqueConstraints = {
                // Combinação agencia+numeroConta for única no sistema inteiro:
                //@UniqueConstraint(name = "uk_conta_ag_num", columnNames = {"agencia", "numero_conta"})
                // Se quiser por banco também, use:
                 @UniqueConstraint(name = "uk_conta_banco_ag_num", columnNames = {"banco_id", "agencia", "numero_conta"})
                // Se quiser por empresa também, use:
                // @UniqueConstraint(name = "uk_conta_emp_banco_ag_num", columnNames = {"empresa_id", "banco_id", "agencia", "numero_conta"})
        }
)
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Banco ao qual pertence a conta
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banco_id", nullable = false)
    private Banco banco;

    // Empresa dona da conta (deixe nullable=true para compatibilizar com o loader atual;
    // quando ajustar o loader para informar empresa, pode trocar para nullable=false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    @Column(name = "agencia", length = 20, nullable = false)
    private String agencia;

    @Column(name = "numero_conta", length = 30, nullable = false)
    private String numeroConta;

    // Corrente, Poupança, etc. (opcional)
    @Column(length = 30)
    private String tipo;

    public ContaBancaria() {
    }

    public ContaBancaria(Banco banco, Empresa empresa, String agencia, String numeroConta, String tipo) {
        this.banco = banco;
        this.empresa = empresa;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        String bancoNome = (banco != null) ? banco.getNome() : "Banco N/A";
        return bancoNome + " - Ag." + agencia + " Conta " + numeroConta + (tipo != null && !tipo.isBlank() ? " (" + tipo + ")" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContaBancaria)) return false;
        ContaBancaria that = (ContaBancaria) o;
        // Se id existir, compara por id; senão, usa os campos de unicidade
        if (id != null && that.id != null) return Objects.equals(id, that.id);
        return Objects.equals(agencia, that.agencia) &&
                Objects.equals(numeroConta, that.numeroConta);
        // Se mudar a constraint para incluir banco/empresa, inclua-os aqui também.
    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hash(id) : Objects.hash(agencia, numeroConta);
    }
}
