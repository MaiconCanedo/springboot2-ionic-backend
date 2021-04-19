package com.nelioalves.cursomc.api.v1.model;

import com.nelioalves.cursomc.domain.entity.Estado;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EstadoModel implements Serializable {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String nome;

    public EstadoModel() {
    }

    public EstadoModel(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String nome) {
        this.id = id;
        this.nome = nome;
    }

    public EstadoModel(Estado estado) {
        this(estado.getId(), estado.getNome());
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