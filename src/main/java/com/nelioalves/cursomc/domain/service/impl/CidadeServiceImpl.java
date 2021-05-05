package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.domain.entity.Estado;
import com.nelioalves.cursomc.domain.entity.dto.CidadeDTO;
import com.nelioalves.cursomc.domain.repository.CidadeRepository;
import com.nelioalves.cursomc.domain.service.CidadeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository repository;

    public CidadeServiceImpl(CidadeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CidadeDTO> findCidadesByEstado(final Estado estado) {
        return repository.findAllByEstado(estado);
    }
}
