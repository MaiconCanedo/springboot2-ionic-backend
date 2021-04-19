package com.nelioalves.cursomc.api.v1.assembler;

import com.nelioalves.cursomc.api.v1.model.input.PedidoInput;
import com.nelioalves.cursomc.domain.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PedidoDisassembler {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "instante", ignore = true)
    Pedido toDomain(PedidoInput pedidoInput);

}
