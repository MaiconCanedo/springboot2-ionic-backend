package com.nelioalves.cursomc.domain.repository;

import com.nelioalves.cursomc.domain.entity.Categoria;
import com.nelioalves.cursomc.domain.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Optional<Produto> findByCodigo(String codigo);

    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(String nome, Collection<Categoria> categorias, Pageable pageable);
}