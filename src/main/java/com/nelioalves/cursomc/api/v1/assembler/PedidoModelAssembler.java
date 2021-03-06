package com.nelioalves.cursomc.api.v1.assembler;

import com.nelioalves.cursomc.api.v1.model.PedidoModel;
import com.nelioalves.cursomc.domain.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PedidoModelAssembler {

    @Mapping(target = "id", source = "codigo")
    PedidoModel toModel(Pedido pedido);

}
