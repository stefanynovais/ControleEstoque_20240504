package com.controleestoque.api_estoque.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.repository.ClienteRepository;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente c) { return clienteRepository.save(c); }
    public List<Cliente> listar() { return clienteRepository.findAll(); }
    public Cliente buscar(Long id) { return clienteRepository.findById(id).orElse(null); }
    public void deletar(Long id) { clienteRepository.deleteById(id); }
}
