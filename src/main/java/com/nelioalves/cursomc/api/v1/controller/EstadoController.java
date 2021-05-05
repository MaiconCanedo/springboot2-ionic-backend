package com.nelioalves.cursomc.api.v1.controller;

import com.nelioalves.cursomc.api.v1.assembler.CidadeModelAssembler;
import com.nelioalves.cursomc.api.v1.assembler.EstadoModelAssembler;
import com.nelioalves.cursomc.api.v1.model.CidadeModel;
import com.nelioalves.cursomc.api.v1.model.EstadoModel;
import com.nelioalves.cursomc.domain.entity.dto.CidadeDTO;
import com.nelioalves.cursomc.domain.service.EstadoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/estados")
public class EstadoController {

    private final EstadoService service;
    private final EstadoModelAssembler assembler;
    private final CidadeModelAssembler cidadeModelAssembler;

    public EstadoController(EstadoService service, EstadoModelAssembler assembler, CidadeModelAssembler cidadeModelAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.cidadeModelAssembler = cidadeModelAssembler;
    }

    @GetMapping
    public List<EstadoModel> findAll() {
        return service.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public EstadoModel find(@PathVariable Integer id) {
        final var estado = service.findOrFail(id);
        return assembler.toModel(estado);
    }

    @GetMapping("{id}/cidades")
    public List<CidadeModel> findCidades(@PathVariable Integer id) {
        final List<CidadeDTO> cidades = service.findCidades(id);
        return cidadeModelAssembler.toModels(cidades);
    }
}
