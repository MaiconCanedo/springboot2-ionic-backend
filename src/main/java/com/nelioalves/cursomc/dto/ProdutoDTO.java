package com.nelioalves.cursomc.dto;

import com.nelioalves.cursomc.domain.Produto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private Integer id;
    private String nome;
    private Double preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Integer id, String nome, Double preco) {
        this(nome, preco);
        this.id = id;
    }

    public ProdutoDTO(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public ProdutoDTO(Produto produto) {
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