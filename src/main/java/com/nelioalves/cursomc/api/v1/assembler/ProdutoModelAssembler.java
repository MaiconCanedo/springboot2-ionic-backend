package com.nelioalves.cursomc.api.v1.assembler;

import com.nelioalves.cursomc.api.v1.model.ProdutoModel;
import com.nelioalves.cursomc.domain.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProdutoModelAssembler {

    @Mapping(target = "id", source = "codigo")
    ProdutoModel toModel(Produto produto);
}
