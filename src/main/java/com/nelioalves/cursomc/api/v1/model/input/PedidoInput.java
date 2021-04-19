package com.nelioalves.cursomc.api.v1.model.input;

import com.nelioalves.cursomc.domain.entity.Cliente;
import com.nelioalves.cursomc.domain.entity.Endereco;
import com.nelioalves.cursomc.domain.entity.ItemPedido;
import com.nelioalves.cursomc.domain.entity.Pagamento;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PedidoInput {

    private Pagamento pagamento;

    private Cliente cliente;

    private Endereco enderecoDeEntrega;

    private Set<ItemPedido> itens = new HashSet<>();

}
