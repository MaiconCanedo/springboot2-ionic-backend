package com.nelioalves.cursomc.domain.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nelioalves.cursomc.domain.entity.enums.EstadoPagamento;
import com.nelioalves.cursomc.infrastructure.converter.EstadoPagamentoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
public abstract class Pagamento {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Convert(converter = EstadoPagamentoConverter.class)
    private EstadoPagamento estado;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        this(estado, pedido);
        this.id = id;
    }

    public Pagamento(EstadoPagamento estado, Pedido pedido) {
        this.estado = estado;
        this.pedido = pedido;
    }
}
