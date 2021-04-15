package com.nelioalves.cursomc.domain.repository;

import com.nelioalves.cursomc.domain.entity.Categoria;
import com.nelioalves.cursomc.domain.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

//    @Query("SELECT DISTINCT pd FROM Produto pd INNER JOIN pd.categorias cat WHERE pd.nome LIKE %:nome% AND cat IN :categorias")
//    Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageable);
    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageable);
}