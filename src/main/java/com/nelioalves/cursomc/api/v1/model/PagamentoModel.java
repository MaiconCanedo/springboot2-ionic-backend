package com.nelioalves.cursomc.api.v1.model;

import com.nelioalves.cursomc.domain.entity.enums.EstadoPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoModel {

    private Integer id;
    private EstadoPagamento estado;

}
