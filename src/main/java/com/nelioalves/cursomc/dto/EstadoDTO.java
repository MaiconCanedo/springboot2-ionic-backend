package com.nelioalves.cursomc.dto;

import com.nelioalves.cursomc.domain.Estado;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EstadoDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String nome;

    public EstadoDTO() {
    }

    public EstadoDTO(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String nome) {
        this.id = id;
        this.nome = nome;
    }

    public EstadoDTO(Estado estado) {
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