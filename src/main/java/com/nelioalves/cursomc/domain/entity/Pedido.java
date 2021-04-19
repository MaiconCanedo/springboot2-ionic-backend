package com.nelioalves.cursomc.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @CreationTimestamp
    private OffsetDateTime instante;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_de_entrega_id")
    private Endereco enderecoDeEntrega;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido(Integer id, OffsetDateTime instante, Cliente cliente, Endereco enderecoDeEntrega) {
        this(instante, cliente, enderecoDeEntrega);
        this.id = id;
    }

    public Pedido(OffsetDateTime instante, Cliente cliente, Endereco enderecoDeEntrega) {
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final StringBuffer stringBuffer = new StringBuffer("Pedido número: ");
        stringBuffer.append(id);
        stringBuffer.append(", Instante: ").append(dateFormat.format(instante));
        stringBuffer.append(", Cliente: ").append(cliente.getNome());
        stringBuffer.append(", Situação do pagamento: ").append(pagamento.getEstado().getDescricao());
        stringBuffer.append("\nDetalhes:\n");
        for (ItemPedido item : itens) {
            stringBuffer.append(item);
        }
        stringBuffer.append("Valor total: ").append(numberFormat.format(getValorTotal()));
        return stringBuffer.toString();
    }

    public Double getValorTotal() {
        Double soma = 0.0;

        for (ItemPedido item : itens) {
            soma += item.getSubTotal();
        }

        return soma;
    }

    @PrePersist
    private void prePersist() {
        this.codigo = UUID.randomUUID().toString();
    }

}
