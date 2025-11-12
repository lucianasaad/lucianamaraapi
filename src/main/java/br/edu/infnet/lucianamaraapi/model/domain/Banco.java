package br.edu.infnet.lucianamaraapi.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Objects;

@Entity
@Table(
        name = "banco",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_banco_nome", columnNames = {"nome"}),
                @UniqueConstraint(name = "uk_banco_codigo", columnNames = {"codigo"})
        }
)
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 120, nullable = false)
    private String nome;

    // Ex.: "001" Banco do Brasil, "237" Bradesco
    @Column(length = 10, nullable = true)
    private String codigo;

    public Banco() {
    }

    public Banco(String nome) {
        this.nome = nome;
    }

    public Banco(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        String rotulo = (codigo != null && !codigo.isBlank()) ? " (" + codigo + ")" : "";
        return nome + rotulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Banco)) return false;
        Banco banco = (Banco) o;
        // Regra: se id existir, comparar por id; senão, por nome+codigo
        if (id != null && banco.id != null) return Objects.equals(id, banco.id);
        return Objects.equals(nome, banco.nome) && Objects.equals(codigo, banco.codigo);
    }

    @Override
    public int hashCode() {
        return (id != null) ? Objects.hash(id) : Objects.hash(nome, codigo);
    }
}
