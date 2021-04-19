package com.nelioalves.cursomc.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.nelioalves.cursomc.domain.entity.enums.EstadoPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private OffsetDateTime dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private OffsetDateTime dataPagamento;

    public PagamentoComBoleto(EstadoPagamento estadoPagamento, Pedido pedido, OffsetDateTime dataVencimento, OffsetDateTime dataPagamento) {
        super(estadoPagamento, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

}
