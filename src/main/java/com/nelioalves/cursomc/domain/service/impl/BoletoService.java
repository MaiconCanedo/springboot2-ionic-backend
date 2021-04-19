package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.domain.entity.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagamento, OffsetDateTime instanteDoPedido) {
        final var dataVencimento = instanteDoPedido.plusDays(7);
        pagamento.setDataVencimento(dataVencimento);
    }
}
