package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.ItemPedido;
import com.nelioalves.cursomc.domain.entity.PagamentoComBoleto;
import com.nelioalves.cursomc.domain.entity.Pedido;
import com.nelioalves.cursomc.domain.entity.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.repository.ItemPedidoRepository;
import com.nelioalves.cursomc.domain.repository.PagamentoRepository;
import com.nelioalves.cursomc.domain.repository.PedidoRepository;
import com.nelioalves.cursomc.core.security.UserSS;
import com.nelioalves.cursomc.domain.exception.AuthorizationException;
import com.nelioalves.cursomc.domain.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> pedido = repository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public List<Pedido> findAll() {
        List<Pedido> pedidos = repository.findAll();
        if (pedidos.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Pedido.class.getName());
        return pedidos;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user = UserService.authenticated();
        if (user == null) throw new AuthorizationException("Acesso negado");
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Pedido> pedidos = repository.findByCliente(clienteService.find(user.getId()), pageRequest);
        if (pedidos.getContent().isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Pedido.class.getName());
        return pedidos;
    }

    @Transactional
    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            boletoService.preencherPagamentoComBoleto(((PagamentoComBoleto) pedido.getPagamento()), pedido.getInstante());
        }
        pedido = repository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for (ItemPedido item : pedido.getItens()) {
            item.setDesconto(0.0);
            item.setProduto(produtoService.find(item.getProduto().getId()));
            item.setPreco(item.getProduto().getPreco());
            item.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationHtmlEmail(pedido);
        return pedido;
    }
}