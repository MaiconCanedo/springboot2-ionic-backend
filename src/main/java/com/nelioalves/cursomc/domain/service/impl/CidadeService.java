package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.api.v1.model.CidadeModel;
import com.nelioalves.cursomc.domain.repository.CidadeRepository;
import com.nelioalves.cursomc.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public List<CidadeModel> findCidades(Integer id) {
        List<CidadeModel> cidades = repository.findByEstadoId(id);
        if (cidades.isEmpty()) throw new NotFoundException("Nenhuma cidade do estado de id " + id + " foi encontrada");
        return cidades/*.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList())*/;
    }
}