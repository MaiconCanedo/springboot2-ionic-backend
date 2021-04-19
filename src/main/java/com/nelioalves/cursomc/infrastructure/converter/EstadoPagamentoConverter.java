package com.nelioalves.cursomc.infrastructure.converter;

import com.nelioalves.cursomc.domain.entity.enums.EstadoPagamento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EstadoPagamentoConverter implements AttributeConverter<EstadoPagamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EstadoPagamento estado) {
        return estado != null ? estado.getCodigo() : null;
    }

    @Override
    public EstadoPagamento convertToEntityAttribute(Integer code) {
        return EstadoPagamento.parseOrThrow(code);
    }
}
