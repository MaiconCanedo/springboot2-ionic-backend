package com.nelioalves.cursomc.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class Produto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String codigo;

    private String nome;

    @Column(scale = 10, precision = 2)
    private Double preco;

    @ManyToMany
    @JoinTable(
            name = "produto_categoria",
            joinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "produto_fk"), nullable = false),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id" ,foreignKey = @ForeignKey(name = "categoria_fk"), nullable = false)
    )
    private List<Categoria> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Produto(Integer id, String nome, Double preco) {
        this(nome, preco);
        this.id = id;
    }

    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @JsonIgnore
    public List<Pedido> getPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        for (ItemPedido itemPedido : itens) {
            pedidos.add(itemPedido.getPedido());
        }
        return pedidos;
    }

    @PrePersist
    private void prePersist() {
        this.codigo = UUID.randomUUID().toString();
    }
}
