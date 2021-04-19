package com.nelioalves.cursomc.api.controller;

import com.nelioalves.cursomc.api.controller.utils.URL;
import com.nelioalves.cursomc.api.model.ProdutoModel;
import com.nelioalves.cursomc.domain.entity.Produto;
import com.nelioalves.cursomc.domain.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    private final ProdutoService service;

    public ProdutoResource(ProdutoService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public Produto findById(@PathVariable Integer id) {
        return service.find(id);
    }

    public Page<Produto> findAll(
            @PageableDefault(size = 24, sort = "nome", direction = ASC) Pageable pageable
    ) {
        return service.findPage(pageable);
    }

    @GetMapping
    public Page<ProdutoModel> findAll(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @PageableDefault(size = 24, sort = "nome", direction = ASC) Pageable pageable
    ) {
        return service.search(URL.decodeParam(nome), URL.decodeIntList(categorias), pageable);
    }
}