package com.nelioalves.cursomc.resources.exceptions;

import com.nelioalves.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.buscar(id));
    }

    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(@PathVariable Integer page) {
        return ResponseEntity.ok().body(service.listar(page));
    }
}
