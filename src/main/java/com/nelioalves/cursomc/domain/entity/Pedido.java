package com.nelioalves.cursomc.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date instante;

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

    public Pedido() {
    }

    public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
        this(instante, cliente, enderecoDeEntrega);
        this.id = id;
    }

    public Pedido(Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final StringBuffer sb = new StringBuffer("Pedido número: ");
        sb.append(id);
        sb.append(", Instante: ").append(dateFormat.format(instante));
        sb.append(", Cliente: ").append(cliente.getNome());
        sb.append(", Situação do pagamento: ").append(pagamento.getEstado().getDescricao());
        sb.append("\nDetalhes:\n");
        for (ItemPedido item : itens) {
            sb.append(item);
        }
        sb.append("Valor total: ").append(numberFormat.format(getValorTotal()));
        return sb.toString();
    }

    public Double getValorTotal() {
        Double soma = 0.0;
        for (ItemPedido item : itens) {
            soma += item.getSubTotal();
        }
        return soma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }
}
