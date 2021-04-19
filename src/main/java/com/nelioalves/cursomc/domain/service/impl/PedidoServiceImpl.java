package com.nelioalves.cursomc.domain.service.impl;

import com.nelioalves.cursomc.core.security.UserSecurityService;
import com.nelioalves.cursomc.domain.entity.ItemPedido;
import com.nelioalves.cursomc.domain.entity.PagamentoComBoleto;
import com.nelioalves.cursomc.domain.entity.Pedido;
import com.nelioalves.cursomc.domain.entity.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.exception.AuthorizationException;
import com.nelioalves.cursomc.domain.exception.NotFoundException;
import com.nelioalves.cursomc.domain.repository.ItemPedidoRepository;
import com.nelioalves.cursomc.domain.repository.PagamentoRepository;
import com.nelioalves.cursomc.domain.repository.PedidoRepository;
import com.nelioalves.cursomc.domain.service.EmailService;
import com.nelioalves.cursomc.domain.service.PedidoService;
import com.nelioalves.cursomc.domain.service.ProdutoService;
import com.nelioalves.cursomc.domain.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;
    private final BoletoService boletoService;
    private final PagamentoRepository pagamentoRepository;
    private final ProdutoService produtoService;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteService clienteService;
    private final EmailService emailService;

    public PedidoServiceImpl(PedidoRepository repository,
                             BoletoService boletoService,
                             PagamentoRepository pagamentoRepository,
                             ProdutoService produtoService,
                             ItemPedidoRepository itemPedidoRepository,
                             ClienteService clienteService,
                             EmailService emailService) {
        this.repository = repository;
        this.boletoService = boletoService;
        this.pagamentoRepository = pagamentoRepository;
        this.produtoService = produtoService;
        this.itemPedidoRepository = itemPedidoRepository;
        this.clienteService = clienteService;
        this.emailService = emailService;
    }

    public Optional<Pedido> find(Integer id) {
        return repository.findById(id);
    }

    public Pedido findOrFail(Integer id) {
        return  find(id)
                .orElseThrow(() -> new NotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Page<Pedido> findAll(Pageable pageable) {
        final var user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException("Acesso negado");

        return repository.findByCliente(clienteService.find(user.getId()), pageable);
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        pedido.setId(null);
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
            item.setProduto(produtoService.findOrFail(item.getProduto().getId()));
            item.setPreco(item.getProduto().getPreco());
            item.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationHtmlEmail(pedido);

        return pedido;
    }
}
