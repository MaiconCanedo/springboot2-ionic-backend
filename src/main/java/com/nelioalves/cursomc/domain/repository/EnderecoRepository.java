package com.nelioalves.cursomc.domain.repository;

import com.nelioalves.cursomc.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}