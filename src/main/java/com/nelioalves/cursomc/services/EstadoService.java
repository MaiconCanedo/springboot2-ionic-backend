package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.dto.EstadoDTO;
import com.nelioalves.cursomc.repositories.EstadoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<EstadoDTO> findAll() {
        List<Estado> estados = repository.findAllByOrderByNome();
        if (estados.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Estado.class.getName());
        return estados.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
    }

    public Estado find(Integer id) {
        Optional<Estado> estado = repository.findById(id);
        return estado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
    }

}
