package com.nelioalves.cursomc.api.model;

import com.nelioalves.cursomc.domain.entity.Categoria;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CategoriaModel implements Serializable {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Size(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    public CategoriaModel() {
    }

    public CategoriaModel(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoriaModel(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
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
