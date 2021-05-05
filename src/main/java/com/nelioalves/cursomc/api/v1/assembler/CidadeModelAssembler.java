package com.nelioalves.cursomc.api.v1.assembler;

import com.nelioalves.cursomc.api.v1.model.CidadeModel;
import com.nelioalves.cursomc.domain.entity.dto.CidadeDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface CidadeModelAssembler {

    @Named("toModel")
    CidadeModel toModel(CidadeDTO cidade);

    @IterableMapping(qualifiedByName = "toModel")
    List<CidadeModel> toModels(List<CidadeDTO> cidades);

}
