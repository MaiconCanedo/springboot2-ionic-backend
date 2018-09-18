package com.nelioalves.cursomc.domain.enums;

public enum TipoCliente {

    PESSOA_FISICA(1, "Pessoa Física"),
    PESSOA_JURIDICA(2, "Pessoa Jurídica");

    private Integer codigo;
    private String descricao;

    private TipoCliente(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoCliente toEnum(Integer codigo) {
        if (codigo == null) return null;

        for (TipoCliente tipoCliente: TipoCliente.values()) {
            if (tipoCliente.codigo.equals(codigo)) return tipoCliente;
        }

        throw new IllegalArgumentException("Código inválido: " + codigo);
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}