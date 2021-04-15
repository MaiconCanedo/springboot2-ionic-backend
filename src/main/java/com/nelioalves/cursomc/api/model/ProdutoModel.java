package com.nelioalves.cursomc.api.model;

import com.nelioalves.cursomc.domain.entity.Produto;

import java.io.Serializable;

public class ProdutoModel implements Serializable {

    private Integer id;
    private String nome;
    private Double preco;

    public ProdutoModel() {
    }

    public ProdutoModel(Integer id, String nome, Double preco) {
        this(nome, preco);
        this.id = id;
    }

    public ProdutoModel(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public ProdutoModel(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco());
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}