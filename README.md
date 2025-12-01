# ControleEstoque-<20240504>

## Descrição do Projeto
API REST de controle de estoque desenvolvida em **Spring Boot**.  
O sistema permite gerenciar produtos, clientes e vendas, incluindo **baixa automática de estoque** ao registrar uma venda, e tratamento de erros com **rollback** quando a quantidade solicitada ultrapassa o estoque disponível.

---

## Funcionalidades

1. **Cliente**
   - Criar, consultar, atualizar e excluir clientes.
   - Consultar cliente por ID ou listar todos.

2. **Produto**
   - Criar, consultar, atualizar e excluir produtos.
   - Consultar estoque disponível antes da venda.

3. **Venda**
   - Registrar novas vendas associadas a clientes.
   - Cada venda contém itens com produto, quantidade e preço unitário.
   - Verifica disponibilidade de estoque antes de finalizar a venda.
   - Baixa automática do estoque se houver quantidade suficiente.
   - Rollback automático em caso de estoque insuficiente, mantendo o estoque original.

---

## Requisitos de Implementação

- Spring Boot 3.x
- Java 17+
- Maven ou Gradle
- Banco de dados MySQL (ou H2 para testes)
- JPA/Hibernate para persistência
- Lombok para reduzir boilerplate de código
- Jackson para serialização JSON

---

## Endpoints Principais

### Cliente
- `POST /api/clientes` → Criar novo cliente  
- `GET /api/clientes` → Listar todos os clientes  
- `GET /api/clientes/{id}` → Consultar cliente por ID  
- `PUT /api/clientes/{id}` → Atualizar cliente  
- `DELETE /api/clientes/{id}` → Excluir cliente  

### Produto
- `POST /api/produtos` → Criar produto  
- `GET /api/produtos` → Listar produtos  
- `GET /api/produtos/{id}` → Consultar produto por ID  
- `PUT /api/produtos/{id}` → Atualizar produto  
- `DELETE /api/produtos/{id}` → Excluir produto  

### Venda
- `POST /api/vendas` → Registrar nova venda  
- `GET /api/vendas/{id}` → Consultar venda por ID  

---

## Como Inicializar a API

1. Clonar o repositório:
```bash
git clone [(https://github.com/stefanynovais/ControleEstoque_StefanyNovais.git)]
