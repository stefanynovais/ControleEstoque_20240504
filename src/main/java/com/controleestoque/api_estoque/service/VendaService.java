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

    @Transactional(readOnly = true)
    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com id: " + id));
    }

    @Transactional  // garante rollback se algo der errado
    public Venda registrarVenda(VendaDTO dto) {

        // 1. Busca o cliente da venda
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // 2. Cria a venda vazia
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setItens(new ArrayList<>());  // inicia a lista de itens

        // 3️⃣ Primeiro: Verificar estoque de todos os itens
        for (VendaItemDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));

            if (produto.getEstoque().getQuantidade() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }

        // 4️⃣ Depois: Dar baixa no estoque e criar os itens
        for (VendaItemDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId()).get();

            // Baixa no estoque
            produto.getEstoque().setQuantidade(
                    produto.getEstoque().getQuantidade() - itemDTO.getQuantidade()
            );
            produtoRepository.save(produto);

            // Cria item da venda
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDTO.getQuantidade());
            itemVenda.setPrecoUnitario(produto.getPreco());
            itemVenda.setVenda(venda);  // associa ao pai

            // Adiciona item na lista da venda
            venda.getItens().add(itemVenda);
        }

        // 5️⃣ Salva a venda inteira com todos os itens
        return vendaRepository.save(venda);
    }
}
