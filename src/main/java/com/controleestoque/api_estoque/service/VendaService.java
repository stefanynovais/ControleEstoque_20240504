package com.controleestoque.api_estoque.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import com.controleestoque.api_estoque.dto.VendaDTO;
import com.controleestoque.api_estoque.dto.VendaItemDTO;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

   @Transactional           // garante rollback se algo der errado
public Venda registrarVenda(VendaDTO dto) {

    // 1. Busca o cliente da venda
    Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

    // 2. Cria a venda vazia
    Venda venda = new Venda();
    venda.setCliente(cliente);
    venda.setItens(new ArrayList<>());  // inicia a lista de itens

    // 3. Para cada item da venda enviada no JSON
    for (VendaItemDTO itemDTO : dto.getItens()) {

        // 3.1 Busca o produto
        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));

        // 3.2 Verifica se tem estoque suficiente
        if (produto.getEstoque().getQuantidade() < itemDTO.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        // 3.3 Dá baixa do estoque
        produto.getEstoque().setQuantidade(
                produto.getEstoque().getQuantidade() - itemDTO.getQuantidade()
        );

        // Salva o novo estoque
        produtoRepository.save(produto);

        // 3.4 Cria o item da venda
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setProduto(produto);
        itemVenda.setQuantidade(itemDTO.getQuantidade());
        itemVenda.setPrecoUnitario(produto.getPreco());
        itemVenda.setVenda(venda);    // associa ao pai

        // 3.5 Adiciona o item na lista da venda
        venda.getItens().add(itemVenda);
    }

    // 4. Salva a venda inteira com todos os itens
    return vendaRepository.save(venda);
}
}