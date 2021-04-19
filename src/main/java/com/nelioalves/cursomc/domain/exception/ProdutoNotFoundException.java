package com.nelioalves.cursomc.domain.exception;

public class ProdutoNotFoundException extends NotFoundException {

    public ProdutoNotFoundException(Integer id) {
        super("Produto de id %s1 não encontrado!", id);
    }

}
