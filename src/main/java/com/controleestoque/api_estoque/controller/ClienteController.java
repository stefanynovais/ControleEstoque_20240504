package com.controleestoque.api_estoque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.service.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public Cliente criar(@RequestBody Cliente c) { return clienteService.salvar(c); }

    @GetMapping
    public List<Cliente> listar() { return clienteService.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        Cliente cliente = clienteService.buscar(id);
        return cliente != null ? ResponseEntity.ok(cliente)
                               : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) { clienteService.deletar(id); }
}

