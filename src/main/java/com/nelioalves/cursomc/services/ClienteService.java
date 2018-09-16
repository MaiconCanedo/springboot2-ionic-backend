package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente buscar(Integer id) {
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Page<Cliente> listar(Integer page) {
        Pageable pageable = PageRequest.of(page, 100, Sort.Direction.ASC, "nome");
        Page<Cliente> clientes = repository.findAll(pageable);
        if (clientes.getContent().size() == 0) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Cliente.class.getName());
        return clientes;
    }
}