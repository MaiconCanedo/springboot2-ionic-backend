package com.nelioalves.cursomc.api.v1.controller;

import com.nelioalves.cursomc.api.v1.assembler.PedidoDisassembler;
import com.nelioalves.cursomc.api.v1.assembler.PedidoModelAssembler;
import com.nelioalves.cursomc.api.v1.model.PedidoModel;
import com.nelioalves.cursomc.api.v1.model.input.PedidoInput;
import com.nelioalves.cursomc.domain.entity.Pedido;
import com.nelioalves.cursomc.domain.service.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping(value = "api/v1/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoModelAssembler assembler;
    private final PedidoDisassembler disassembler;

    public PedidoController(PedidoService service, PedidoModelAssembler assembler, PedidoDisassembler disassembler) {
        this.service = service;
        this.assembler = assembler;
        this.disassembler = disassembler;
    }

    @GetMapping("{id}")
    public PedidoModel find(@PathVariable Integer id) {
        final var pedido = service.findOrFail(id);
        return assembler.toModel(pedido);
    }

    @GetMapping
    public Page<PedidoModel> findAll(@PageableDefault(size = 24, sort = "instante", direction = DESC) Pageable pageable) {
        return service.findAll(pageable)
                .map(assembler::toModel);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody PedidoInput pedidoInput) {
        final Pedido pedido = disassembler.toDomain(pedidoInput);
        service.save(pedido);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedido.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
