package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Estado;
import com.nelioalves.cursomc.domain.entity.dto.CidadeDTO;

import java.util.List;
import java.util.Optional;

public interface EstadoService {

    List<Estado> findAll();

    Optional<Estado> find(Integer id);

    Estado findOrFail(Integer id);

    List<CidadeDTO> findCidades(Integer id);
}
