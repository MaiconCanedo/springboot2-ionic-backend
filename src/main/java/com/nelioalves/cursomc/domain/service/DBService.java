package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.*;
import com.nelioalves.cursomc.domain.entity.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.entity.enums.Perfil;
import com.nelioalves.cursomc.domain.entity.enums.TipoCliente;
import com.nelioalves.cursomc.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;

@Service
public class DBService {

    private static final ZoneOffset SAO_PAULO_OFFSET = ZoneOffset.of("-03:00");

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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

        Cliente cliente1 = new Cliente("Maria Silva", "springbootalura@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA, passwordEncoder.encode("123"));
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Cliente cliente2 = new Cliente("Ana Costa", "maicon.canedo1995@gmail.com", "41921726318", TipoCliente.PESSOA_FISICA, passwordEncoder.encode("123"));
        cliente1.getTelefones().addAll(Arrays.asList("89312343", "628387974"));
        cliente2.addPerfil(Perfil.ADMIN);

        Endereco endereco1 = new Endereco("Rua Flores", "300", "Apto 203", "Jardim", "38220834", cliente1, cidade1);
        Endereco endereco2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);
        Endereco endereco3 = new Endereco("Avenida Floriano", "2106", null, "Centro", "281777012", cliente2, cidade2);

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
        cliente2.getEnderecos().add(endereco3);

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));

        var pedido1 = new Pedido(OffsetDateTime.of(2017, 9, 30, 10, 32, 0, 0, SAO_PAULO_OFFSET), cliente1, endereco1);
        var pedido2 = new Pedido(OffsetDateTime.of(2017, 10, 10, 19, 35, 0, 0, SAO_PAULO_OFFSET), cliente1, endereco2);

        var pagamento1 = new PagamentoComCartao(EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamento1);

        var pagamento2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, pedido2, OffsetDateTime.of(2017, 10, 20, 0, 0, 0, 0, SAO_PAULO_OFFSET), null);
        pedido2.setPagamento(pagamento2);

        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

        ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2_000.00);
        ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
        ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);

        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItens().addAll(Collections.singletonList(itemPedido3));

        produto1.getItens().add(itemPedido1);
        produto2.getItens().add(itemPedido3);
        produto3.getItens().add(itemPedido2);

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));

        Produto p12 = new Produto(null, "Produto 12", 10.00);
        Produto p13 = new Produto(null, "Produto 13", 10.00);
        Produto p14 = new Produto(null, "Produto 14", 10.00);
        Produto p15 = new Produto(null, "Produto 15", 10.00);
        Produto p16 = new Produto(null, "Produto 16", 10.00);
        Produto p17 = new Produto(null, "Produto 17", 10.00);
        Produto p18 = new Produto(null, "Produto 18", 10.00);
        Produto p19 = new Produto(null, "Produto 19", 10.00);
        Produto p20 = new Produto(null, "Produto 20", 10.00);
        Produto p21 = new Produto(null, "Produto 21", 10.00);
        Produto p22 = new Produto(null, "Produto 22", 10.00);
        Produto p23 = new Produto(null, "Produto 23", 10.00);
        Produto p24 = new Produto(null, "Produto 24", 10.00);
        Produto p25 = new Produto(null, "Produto 25", 10.00);
        Produto p26 = new Produto(null, "Produto 26", 10.00);
        Produto p27 = new Produto(null, "Produto 27", 10.00);
        Produto p28 = new Produto(null, "Produto 28", 10.00);
        Produto p29 = new Produto(null, "Produto 29", 10.00);
        Produto p30 = new Produto(null, "Produto 30", 10.00);
        Produto p31 = new Produto(null, "Produto 31", 10.00);
        Produto p32 = new Produto(null, "Produto 32", 10.00);
        Produto p33 = new Produto(null, "Produto 33", 10.00);
        Produto p34 = new Produto(null, "Produto 34", 10.00);
        Produto p35 = new Produto(null, "Produto 35", 10.00);
        Produto p36 = new Produto(null, "Produto 36", 10.00);
        Produto p37 = new Produto(null, "Produto 37", 10.00);
        Produto p38 = new Produto(null, "Produto 38", 10.00);
        Produto p39 = new Produto(null, "Produto 39", 10.00);
        Produto p40 = new Produto(null, "Produto 40", 10.00);
        Produto p41 = new Produto(null, "Produto 41", 10.00);
        Produto p42 = new Produto(null, "Produto 42", 10.00);
        Produto p43 = new Produto(null, "Produto 43", 10.00);
        Produto p44 = new Produto(null, "Produto 44", 10.00);
        Produto p45 = new Produto(null, "Produto 45", 10.00);
        Produto p46 = new Produto(null, "Produto 46", 10.00);
        Produto p47 = new Produto(null, "Produto 47", 10.00);
        Produto p48 = new Produto(null, "Produto 48", 10.00);
        Produto p49 = new Produto(null, "Produto 49", 10.00);
        Produto p50 = new Produto(null, "Produto 50", 10.00);

        categoria1.getProdutos().addAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
                p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));

        p12.getCategorias().add(categoria1);
        p13.getCategorias().add(categoria1);
        p14.getCategorias().add(categoria1);
        p15.getCategorias().add(categoria1);
        p16.getCategorias().add(categoria1);
        p17.getCategorias().add(categoria1);
        p18.getCategorias().add(categoria1);
        p19.getCategorias().add(categoria1);
        p20.getCategorias().add(categoria1);
        p21.getCategorias().add(categoria1);
        p22.getCategorias().add(categoria1);
        p23.getCategorias().add(categoria1);
        p24.getCategorias().add(categoria1);
        p25.getCategorias().add(categoria1);
        p26.getCategorias().add(categoria1);
        p27.getCategorias().add(categoria1);
        p28.getCategorias().add(categoria1);
        p29.getCategorias().add(categoria1);
        p30.getCategorias().add(categoria1);
        p31.getCategorias().add(categoria1);
        p32.getCategorias().add(categoria1);
        p33.getCategorias().add(categoria1);
        p34.getCategorias().add(categoria1);
        p35.getCategorias().add(categoria1);
        p36.getCategorias().add(categoria1);
        p37.getCategorias().add(categoria1);
        p38.getCategorias().add(categoria1);
        p39.getCategorias().add(categoria1);
        p40.getCategorias().add(categoria1);
        p41.getCategorias().add(categoria1);
        p42.getCategorias().add(categoria1);
        p43.getCategorias().add(categoria1);
        p44.getCategorias().add(categoria1);
        p45.getCategorias().add(categoria1);
        p46.getCategorias().add(categoria1);
        p47.getCategorias().add(categoria1);
        p48.getCategorias().add(categoria1);
        p49.getCategorias().add(categoria1);
        p50.getCategorias().add(categoria1);

        produtoRepository.saveAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38, p39,
                p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));
    }
}