package com.nelioalves.cursomc.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private final Integer codigo;
    private final String descricao;

    public static Optional<EstadoPagamento> parse(Integer code) {
        return Arrays.stream(values())
                .filter(estado -> estado.codigo.equals(code))
                .findFirst();
    }

    public static EstadoPagamento parseOrThrow(Integer code) {
        return parse(code)
                .orElseThrow(() -> new IllegalArgumentException("Código inválido: " + code));
    }
}
