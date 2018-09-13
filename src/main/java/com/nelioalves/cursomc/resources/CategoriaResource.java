package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar() {
        Categoria categoria1 = new Categoria(1, "Informática");
        Categoria categoria2 = new Categoria(2, "Escritório");

        List<Categoria> categorias = new ArrayList<>(Arrays.asList(new Categoria[] {categoria1, categoria2}));
        return categorias;
    }
}
