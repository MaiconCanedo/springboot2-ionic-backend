package com.nelioalves.cursomc.domain.exception;

public class ProdutoNotFoundException extends NotFoundException {

    public ProdutoNotFoundException(Object codigo) {
        super("Produto de codigo %s1 não encontrado!", codigo);
    }

}
