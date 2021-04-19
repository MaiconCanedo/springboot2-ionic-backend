package com.nelioalves.cursomc.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {

    private Integer id;
    private String nome;
    private Double preco;
}