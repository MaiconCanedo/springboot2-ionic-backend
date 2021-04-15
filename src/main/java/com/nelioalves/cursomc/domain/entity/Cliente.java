package com.nelioalves.cursomc.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nelioalves.cursomc.domain.entity.enums.Perfil;
import com.nelioalves.cursomc.domain.entity.enums.TipoCliente;
import com.nelioalves.cursomc.api.model.ClienteModel;
import com.nelioalves.cursomc.api.model.input.ClienteInput;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    @Column(unique = true)
    private String email;

    private String cpfOuCnpj;
    private Integer tipo;

    @JsonIgnore
    private String senha;

    //    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set<String> telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private Set<Integer> perfis = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
        this(nome, email, cpfOuCnpj, tipo, senha);
        this.id = id;
    }

    public Cliente(String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = (tipo == null) ? null : tipo.getCodigo();
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Cliente(ClienteModel clienteModel) {
        this(clienteModel.getId(), clienteModel.getNome(), clienteModel.getEmail());
    }

    public Cliente(ClienteInput clienteInput) {
        this(clienteInput.getNome(), clienteInput.getEmail(), clienteInput.getCpfOuCnpj(), TipoCliente.toEnum(clienteInput.getTipo()), clienteInput.getSenha());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCodigo();
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(perfil -> Perfil.toEnum(perfil)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCodigo());
    }
}