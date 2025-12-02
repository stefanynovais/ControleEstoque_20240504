package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Fornecedor;
import java.util.Set;
import java.util.HashSet;

import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.FornecedorRepository;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    // Estoque é geralmente manipulado via Produto ou separadamente.

    // GET /api/produtos
    @GetMapping
    public List<Produto> getAllProdutos() {
        // Retorna a lista de produtos. Pode ser necessário configurar DTOs para evitar
        // loops infinitos com JSON.
        return produtoRepository.findAll();
    }

    // GET /api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getCategoriaById(@PathVariable Long id) {
        // Busca a categoria pelo ID. Usa orElse para retornar 404 se não encontrar.
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/produtos
    // Neste método, assumimos que a Categoria e os Fornecedores já existem
    // e seus IDs são passados no corpo da requisição (ProdutoDTO seria o ideal
    // aqui).
    @PostMapping
@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {

    //validando a categoria criada
    if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
        return ResponseEntity.badRequest().build();
    }

    //buscando a  categoria gerenciada
    var categoriaOpt = categoriaRepository.findById(produto.getCategoria().getId());
    if (categoriaOpt.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }
    produto.setCategoria(categoriaOpt.get());

    //validando e associando fornecedores (se vierem como lista de objetos com id)
    if (produto.getFornecedores() != null && !produto.getFornecedores().isEmpty()) {

        //usa o tipo completo para evitar erro de imports
        java.util.Set<com.controleestoque.api_estoque.model.Fornecedor> fornecedoresValidos = new java.util.HashSet<>();

        for (com.controleestoque.api_estoque.model.Fornecedor f : produto.getFornecedores()) {
            if (f != null && f.getId() != null) {
                fornecedorRepository.findById(f.getId())
                    .ifPresent(fornecedoresValidos::add);
            }
        }


        produto.setFornecedores(fornecedoresValidos);
    }

    //produto com Estoque embutido (um objeto Estoque com produto.id),
    //*fiz isso no vídeo, para demonstrar a consulta de estoque* 
    if (produto.getEstoque() != null) {
        produto.getEstoque().setProduto(produto);
    }

    Produto savedProduto = produtoRepository.save(produto);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
}

    // PUT /api/produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {

        return produtoRepository.findById(id)
                .map(produto -> {

                    // Atualiza os dados do produto encontrado
                    produto.setNome(produtoDetails.getNome());

                    Produto updatedProduto = produtoRepository.save(produto);
                    return ResponseEntity.ok(updatedProduto);

                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {

        // Tenta encontrar e deletar
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna código 204 (No Content)
    }
}