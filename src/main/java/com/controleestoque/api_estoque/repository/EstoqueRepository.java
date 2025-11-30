package com.controleestoque.api_estoque.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Estoque;
 
@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    // Métodos personalizados podem ser adicionados aqui (ex: findByNome)
}
