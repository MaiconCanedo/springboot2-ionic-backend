package com.nelioalves.cursomc.dto;

import com.nelioalves.cursomc.domain.Cidade;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CidadeDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Campo obrigatório")
    private String nome;

    public CidadeDTO() {
    }

    public CidadeDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CidadeDTO(Cidade cidade) {
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