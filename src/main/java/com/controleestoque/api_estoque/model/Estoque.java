package com.controleestoque.api_estoque.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "tb_estoques")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    // --- Relacionamento 1:1 (One-to-One) ---
    // É o lado ‘proprietário’ que contém a chave estrangeira (FK).
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false) // Define a FK na tabela tb_estoques.
    private Produto produto;

    // Construtores, Getters e Setters...
    public Estoque() {}

    public Estoque(Integer quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}
