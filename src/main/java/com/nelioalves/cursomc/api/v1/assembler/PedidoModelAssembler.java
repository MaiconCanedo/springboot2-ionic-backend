package com.nelioalves.cursomc.api.v1.assembler;

import com.nelioalves.cursomc.api.v1.model.PedidoModel;
import com.nelioalves.cursomc.domain.entity.Pedido;
import org.mapstruct.Mapper;

@Mapper
public interface PedidoModelAssembler {

    PedidoModel toModel(Pedido pedido);

}
