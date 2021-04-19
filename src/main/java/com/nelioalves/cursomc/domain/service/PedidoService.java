package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    Optional<Pedido> find(Integer id);

    Pedido findOrFail(Integer id);

    List<Pedido> findAll();

    Page<Pedido> findAll(Pageable pageable);

    Pedido save(Pedido pedido);

}
