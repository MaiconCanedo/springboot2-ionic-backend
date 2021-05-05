package com.nelioalves.cursomc.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstadoDetailsModel {

    private Integer id;
    private String nome;
    private List<CidadeModel> cidades;

}
