package com.controleestoque.api_estoque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.dto.VendaDTO;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.service.VendaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody VendaDTO vendaDTO) {
        try {
            Venda venda = vendaService.registrarVenda(vendaDTO);
            return ResponseEntity.ok(venda);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
