package com.nelioalves.cursomc;

import com.nelioalves.cursomc.domain.*;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria categoria1 = new Categoria("Informática");
        Categoria categoria2 = new Categoria("Escritório");

        Produto produto1 = new Produto("Computador", 2000.0);
        Produto produto2 = new Produto("Impressora", 800.0);
        Produto produto3 = new Produto("Mouse", 80.0);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto2));

        produto1.getCategorias().addAll(Arrays.asList(categoria1));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
        produto3.getCategorias().addAll(Arrays.asList(categoria1));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

        Estado estado1 = new Estado("Minas Gerais");
        Estado estado2 = new Estado("São Paulo");

        Cidade cidade1 = new Cidade("Uberlândia", estado1);
        Cidade cidade2 = new Cidade("São Paulo", estado2);
        Cidade cidade3 = new Cidade("Campinas", estado2);

        estado1.getCidades().add(cidade1);
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente("Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Endereco endereco1 = new Endereco("Rua Flores", "300", "Apto 203", "Jardim", "38220834", cliente1, cidade1);
        Endereco endereco2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);

        clienteRepository.save(cliente1);
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
    }
}

