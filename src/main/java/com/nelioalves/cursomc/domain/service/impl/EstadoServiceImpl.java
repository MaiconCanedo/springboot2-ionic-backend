package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.domain.entity.Estado;
import com.nelioalves.cursomc.domain.entity.dto.CidadeDTO;
import com.nelioalves.cursomc.domain.exception.NotFoundException;
import com.nelioalves.cursomc.domain.repository.EstadoRepository;
import com.nelioalves.cursomc.domain.service.CidadeService;
import com.nelioalves.cursomc.domain.service.EstadoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository repository;
    private final CidadeService cidadeService;

    public EstadoServiceImpl(EstadoRepository repository, CidadeService cidadeService) {
        this.repository = repository;
        this.cidadeService = cidadeService;
    }

    @Override
    public List<Estado> findAll() {
        return repository.findAllByOrderByNome();
    }

    @Override
    public Optional<Estado> find(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Estado findOrFail(Integer id) {
        return find(id)
                .orElseThrow(() -> new NotFoundException("Um estado de id %s, não encontrado!"));
    }

    @Override
    public List<CidadeDTO> findCidades(final Integer id) {
        final var estado = findOrFail(id);
        return cidadeService.findCidadesByEstado(estado);
    }

}
