package com.nelioalves.cursomc.domain.repository;

import com.nelioalves.cursomc.domain.entity.Cidade;
import com.nelioalves.cursomc.domain.entity.Estado;
import com.nelioalves.cursomc.domain.entity.dto.CidadeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT " +
            "  new com.nelioalves.cursomc.domain.entity.dto.CidadeDTO(cidade.id, cidade.nome) " +
            "FROM " +
            "  Cidade cidade " +
            "WHERE " +
            "  cidade.estado = :estado " +
            "ORDER BY " +
            "  cidade.nome")
    List<CidadeDTO> findAllByEstado(Estado estado);
}
