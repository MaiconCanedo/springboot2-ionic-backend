package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.api.model.ProdutoModel;
import com.nelioalves.cursomc.domain.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProdutoService {

    Produto find(Integer id);

    List<Produto> findAll();

    Page<Produto> findPage(Pageable pageable);

    Page<ProdutoModel> search(String nome, List<Integer> ids, Pageable pageable);
}
