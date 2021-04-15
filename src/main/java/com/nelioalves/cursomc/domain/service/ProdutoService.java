package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Produto;
import com.nelioalves.cursomc.api.model.ProdutoModel;
import com.nelioalves.cursomc.domain.repository.CategoriaRepository;
import com.nelioalves.cursomc.domain.repository.ProdutoRepository;
import com.nelioalves.cursomc.domain.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> produto = repository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public List<Produto> findAll() {
        List<Produto> produtos = repository.findAll();
        if (produtos.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Produto.class.getName());
        return produtos;
    }

    public Page<Produto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Produto> produtos = repository.findAll(pageRequest);
        if (produtos.getContent().isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Produto.class.getName());
        return produtos;
    }

    public Page<ProdutoModel> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Produto> produtos = repository.findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(nome, categoriaRepository.findAllById(ids), pageRequest);
        if (produtos.getContent().isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Produto.class.getName());
        return produtos.map(produto -> new ProdutoModel(produto));
    }
}