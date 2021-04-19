package com.nelioalves.cursomc.api.v1.model;

import com.nelioalves.cursomc.domain.entity.Cidade;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CidadeModel implements Serializable {

    private Integer id;

    @NotEmpty(message = "Campo obrigatório")
    private String nome;

    public CidadeModel() {
    }

    public CidadeModel(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CidadeModel(Cidade cidade) {
        this(cidade.getId(), cidade.getNome());
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
}