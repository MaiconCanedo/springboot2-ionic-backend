package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido find(Integer id) {
        Optional<Pedido> pedido = repository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Page<Pedido> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 100, Sort.Direction.ASC, "id");
        Page<Pedido> pedidos = repository.findAll(pageable);
        if (pedidos.getContent().size() == 0) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Pedido.class.getName());
        return pedidos;
    }
}