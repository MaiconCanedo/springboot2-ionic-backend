package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.*;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public void instantiateTestDataBase() throws ParseException {

        Categoria categoria1 = new Categoria("Informática");
        Categoria categoria2 = new Categoria("Escritório");
        Categoria categoria3 = new Categoria("Cama mesa e banho");
        Categoria categoria4 = new Categoria("Eletrônicos");
        Categoria categoria5 = new Categoria("Jardinagem");
        Categoria categoria6 = new Categoria("Decoração");
        Categoria categoria7 = new Categoria("Perfumaria");

        Produto produto1 = new Produto("Computador", 2_000.0);
        Produto produto2 = new Produto("Impressora", 800.0);
        Produto produto3 = new Produto("Mouse", 80.0);
        Produto produto4 = new Produto("Mesa de escritório", 300.0);
        Produto produto5 = new Produto("Toalha", 50.0);
        Produto produto6 = new Produto("Colcha", 200.0);
        Produto produto7 = new Produto("Tv true color", 1200.0);
        Produto produto8 = new Produto("Roçadeira", 800.0);
        Produto produto9 = new Produto("Abajur", 100.0);
        Produto produto10 = new Produto("Pendente", 180.0);
        Produto produto11 = new Produto("Shampoo", 90.0);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
        categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
        categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
        categoria5.getProdutos().addAll(Arrays.asList(produto8));
        categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
        categoria7.getProdutos().addAll(Arrays.asList(produto11));

        produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
        produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto4.getCategorias().addAll(Arrays.asList(categoria2));
        produto5.getCategorias().addAll(Arrays.asList(categoria3));
        produto6.getCategorias().addAll(Arrays.asList(categoria3));
        produto7.getCategorias().addAll(Arrays.asList(categoria4));
        produto8.getCategorias().addAll(Arrays.asList(categoria5));
        produto9.getCategorias().addAll(Arrays.asList(categoria6));
        produto10.getCategorias().addAll(Arrays.asList(categoria6));
        produto11.getCategorias().addAll(Arrays.asList(categoria7));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8, produto9, produto10, produto11));

        Estado estado1 = new Estado("Minas Gerais");
        Estado estado2 = new Estado("São Paulo");

        Cidade cidade1 = new Cidade("Uberlândia", estado1);
        Cidade cidade2 = new Cidade("São Paulo", estado2);
        Cidade cidade3 = new Cidade("Campinas", estado2);

        estado1.getCidades().add(cidade1);
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente("Maria Silva", "springbootalura@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Endereco endereco1 = new Endereco("Rua Flores", "300", "Apto 203", "Jardim", "38220834", cliente1, cidade1);
        Endereco endereco2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);

        clienteRepository.save(cliente1);
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido pedido1 = new Pedido(dateFormat.parse("30/09/2017 10:32"), cliente1, endereco1);
        Pedido pedido2 = new Pedido(dateFormat.parse("10/10/2017 19:35"), cliente1, endereco2);

        Pagamento pagamento1 = new PagamentoComCartao(EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamento1);

        Pagamento pagamento2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, pedido2, dateFormat.parse("20/10/2017 00:00"), null);
        pedido2.setPagamento(pagamento2);

        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

        ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2_000.00);
        ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
        ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);

        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItens().addAll(Arrays.asList(itemPedido3));

        produto1.getItens().add(itemPedido1);
        produto2.getItens().add(itemPedido3);
        produto3.getItens().add(itemPedido2);

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }
}
