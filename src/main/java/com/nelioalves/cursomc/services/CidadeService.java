package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.dto.CidadeDTO;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public List<CidadeDTO> findCidades(Integer id) {
        List<CidadeDTO> cidades = repository.findCidades(id);
        if (cidades.isEmpty()) throw new ObjectNotFoundException("Nenhuma cidade do estado de id " + id + " foi encontrada");
        return cidades;
    }
}