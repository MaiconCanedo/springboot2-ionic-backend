package com.nelioalves.cursomc.api.v1.assembler;

import com.nelioalves.cursomc.api.v1.model.EstadoModel;
import com.nelioalves.cursomc.domain.entity.Estado;
import org.mapstruct.Mapper;

@Mapper
public interface EstadoModelAssembler {

    EstadoModel toModel(Estado estado);

}
