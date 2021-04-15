package com.nelioalves.cursomc.api.model;

import com.nelioalves.cursomc.domain.entity.Cliente;
import com.nelioalves.cursomc.domain.service.validation.ClienteUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ClienteUpdate
public class ClienteModel implements Serializable {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Size(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    public ClienteModel() {
    }

    public ClienteModel(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public ClienteModel(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail());
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
}
