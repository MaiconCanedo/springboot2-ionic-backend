package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.dto.CidadeDTO;
import com.nelioalves.cursomc.dto.EstadoDTO;
import com.nelioalves.cursomc.services.CidadeService;
import com.nelioalves.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Estado> find(@PathVariable Integer id) {
        return ResponseEntity.ok(service.find(id));
    }

    @RequestMapping(value = "/{id}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer id) {
        return ResponseEntity.ok(cidadeService.findCidades(id));
    }
}
