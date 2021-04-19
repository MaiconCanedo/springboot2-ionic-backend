package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.domain.entity.Produto;
import com.nelioalves.cursomc.domain.exception.ProdutoNotFoundException;
import com.nelioalves.cursomc.domain.repository.CategoriaRepository;
import com.nelioalves.cursomc.domain.repository.ProdutoRepository;
import com.nelioalves.cursomc.domain.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoServiceImpl(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public Optional<Produto> find(Integer id) {
        return repository.findById(id);
    }

    public Produto findOrFail(Integer id) {
        return find(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    public Page<Produto> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Produto> findAll(String nome, List<Integer> ids, Pageable pageable) {
        return repository.findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(nome, categoriaRepository.findAllById(ids), pageable);
    }
}