package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Estado;
import com.nelioalves.cursomc.domain.entity.dto.CidadeDTO;

import java.util.List;

public interface CidadeService {

    List<CidadeDTO> findCidadesByEstado(Estado estado);

}
