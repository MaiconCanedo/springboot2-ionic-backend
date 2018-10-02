package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.dto.CidadeDTO;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public List<CidadeDTO> findCidades(Integer id) {
        List<CidadeDTO> cidades = repository.findByEstadoId(id);
        if (cidades.isEmpty()) throw new ObjectNotFoundException("Nenhuma cidade do estado de id " + id + " foi encontrada");
        return cidades/*.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList())*/;
    }
}