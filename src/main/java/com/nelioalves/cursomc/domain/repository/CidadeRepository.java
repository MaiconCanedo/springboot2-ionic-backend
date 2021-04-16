package com.nelioalves.cursomc.domain.repository;

import com.nelioalves.cursomc.domain.entity.Cidade;
import com.nelioalves.cursomc.domain.entity.Estado;
import com.nelioalves.cursomc.api.model.CidadeModel;
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
    List<CidadeModel> findByEstadoId(Integer id);

    @Transactional(readOnly = true)
    @Query("SELECT new com.nelioalves.cursomc.api.model.CidadeModel(c.id, c.nome) FROM Cidade c WHERE c.estado.id = :estadoId ORDER BY c.nome")
    List<CidadeModel> findCidades(@Param("estadoId") Integer id);
}