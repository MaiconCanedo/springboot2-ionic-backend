package com.nelioalves.cursomc.api.controller;

import com.nelioalves.cursomc.domain.entity.Cliente;
import com.nelioalves.cursomc.api.model.ClienteModel;
import com.nelioalves.cursomc.api.model.input.ClienteInput;
import com.nelioalves.cursomc.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        return ResponseEntity.ok(service.find(id));
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ResponseEntity<Cliente> findByEmail(@RequestParam(value = "value") String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteModel>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteModel>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                       @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                       @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        return ResponseEntity.ok(service.findPage(page, linesPerPage, orderBy, direction));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteInput clienteInput) {
        Cliente cliente = service.insert(service.fromDTO(clienteInput));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteModel clienteModel, @PathVariable Integer id) {
        clienteModel.setId(id);
        Cliente cliente = service.update(service.fromDTO(clienteModel));
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}