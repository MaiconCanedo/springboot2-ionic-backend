package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.domain.entity.Produto;
import com.nelioalves.cursomc.api.model.ProdutoModel;
import com.nelioalves.cursomc.domain.repository.CategoriaRepository;
import com.nelioalves.cursomc.domain.repository.ProdutoRepository;
import com.nelioalves.cursomc.domain.exception.ObjectNotFoundException;
import com.nelioalves.cursomc.domain.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Produto find(Integer id) {
        Optional<Produto> produto = repository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public List<Produto> findAll() {
        List<Produto> produtos = repository.findAll();
        if (produtos.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Produto.class.getName());
        return produtos;
    }

    public Page<Produto> findPage(Pageable pageable) {
        Page<Produto> produtos = repository.findAll(pageable);

        if (produtos.getContent().isEmpty())
            throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Produto.class.getName());

        return produtos;
    }

    public Page<ProdutoModel> search(String nome, List<Integer> ids, Pageable pageable) {
        Page<Produto> produtos = repository.findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(nome, categoriaRepository.findAllById(ids), pageable);

        if (produtos.getContent().isEmpty())
            throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Produto.class.getName());

        return produtos.map(produto -> new ProdutoModel(produto));
    }
}