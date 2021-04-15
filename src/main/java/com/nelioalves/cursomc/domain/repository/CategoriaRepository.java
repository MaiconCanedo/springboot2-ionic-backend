package com.nelioalves.cursomc.domain.repository;

import com.nelioalves.cursomc.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}