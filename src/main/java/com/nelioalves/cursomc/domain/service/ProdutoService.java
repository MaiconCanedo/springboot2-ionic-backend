package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    Optional<Produto> find(String codigo);

    Optional<Produto> find(Integer id);

    Produto findOrFail(String codigo);

    Produto findOrFail(Integer id);

    Page<Produto> findAll(Pageable pageable);

    Page<Produto> findAll(String nome, List<Integer> ids, Pageable pageable);
}
