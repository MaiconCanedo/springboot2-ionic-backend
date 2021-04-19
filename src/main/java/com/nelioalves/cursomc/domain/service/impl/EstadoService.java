package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.domain.entity.Estado;
import com.nelioalves.cursomc.api.model.EstadoModel;
import com.nelioalves.cursomc.domain.repository.EstadoRepository;
import com.nelioalves.cursomc.domain.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<EstadoModel> findAll() {
        List<Estado> estados = repository.findAllByOrderByNome();
        if (estados.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Estado.class.getName());
        return estados.stream().map(estado -> new EstadoModel(estado)).collect(Collectors.toList());
    }

    public Estado find(Integer id) {
        Optional<Estado> estado = repository.findById(id);
        return estado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
    }

}
