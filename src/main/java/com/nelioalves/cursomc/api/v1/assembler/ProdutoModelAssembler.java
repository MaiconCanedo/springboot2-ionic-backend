package com.nelioalves.cursomc.api.v1.assembler;

import com.nelioalves.cursomc.api.v1.model.ProdutoModel;
import com.nelioalves.cursomc.domain.entity.Produto;
import org.mapstruct.Mapper;

@Mapper
public interface ProdutoModelAssembler {

    ProdutoModel toModel(Produto produto);
}
