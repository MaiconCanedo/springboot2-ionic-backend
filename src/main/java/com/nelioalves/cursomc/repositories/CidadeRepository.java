package com.nelioalves.cursomc.repositories;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.dto.CidadeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    List<Cidade> findByEstado(Estado estado);

    @Transactional(readOnly = true)
    @Query("SELECT new com.nelioalves.cursomc.dto.CidadeDTO(c.id, c.nome) FROM Cidade c WHERE c.estado.id = :estadoId ORDER BY c.nome")
    List<CidadeDTO> findCidades(@Param("estadoId") Integer id);
}