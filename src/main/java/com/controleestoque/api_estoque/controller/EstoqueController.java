package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    //consulta o estoque por id
    @GetMapping("/{id}")
    public ResponseEntity<Estoque> getById(@PathVariable Long id) {
        return estoqueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //consulta o estoque pelo id do produto
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<Estoque> getByProdutoId(@PathVariable Long produtoId) {

        Optional<Produto> produto = produtoRepository.findById(produtoId);
        if (produto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Estoque estoque = estoqueRepository.findByProduto(produto.get());
        if (estoque == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(estoque);
    }

    //criação do estoque para um o produto
    @PostMapping
    public ResponseEntity<Estoque> create(@RequestBody Estoque estoqueRequest) {

        if (estoqueRequest.getProduto() == null || estoqueRequest.getProduto().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Produto produto = produtoRepository
                .findById(estoqueRequest.getProduto().getId())
                .orElse(null);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        estoqueRequest.setProduto(produto);

        Estoque saved = estoqueRepository.save(estoqueRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
