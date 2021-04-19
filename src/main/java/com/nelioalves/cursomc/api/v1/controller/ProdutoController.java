package com.nelioalves.cursomc.api.v1.controller;

import com.nelioalves.cursomc.api.v1.assembler.ProdutoModelAssembler;
import com.nelioalves.cursomc.api.v1.model.ProdutoModel;
import com.nelioalves.cursomc.domain.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping(value = "api/v1/produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoModelAssembler assembler;

    public ProdutoController(ProdutoService service, ProdutoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(value = "{id}")
    public ProdutoModel findById(@PathVariable String id) {
        final var produto = service.findOrFail(id);
        return assembler.toModel(produto);
    }

    @GetMapping
    public Page<ProdutoModel> findAll(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") List<Integer> categorias,
            @PageableDefault(size = 24, sort = "nome", direction = ASC) Pageable pageable
    ) {
        return service.findAll(nome, categorias, pageable)
                .map(assembler::toModel);
    }
}
