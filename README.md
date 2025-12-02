# 📦 ControleEstoque-<20240504>

## 📝 Descrição do Projeto
API REST de controle de estoque desenvolvida em **Spring Boot**.  
Permite gerenciar produtos, clientes e vendas, incluindo:

- 🔹 **Baixa automática de estoque** ao registrar uma venda  
- 🔹 **Rollback** se a quantidade solicitada ultrapassar o estoque disponível  

---

## ⚡ Funcionalidades

### 👤 Cliente
- Criar, consultar, atualizar e excluir clientes  
- Consultar cliente por ID ou listar todos  

### 🍎 Produto
- Criar, consultar, atualizar e excluir produtos  
- Consultar estoque disponível antes da venda  

### 🛒 Venda
- Registrar vendas associadas a clientes  
- Cada venda contém itens com produto, quantidade e preço unitário  
- Verifica disponibilidade de estoque antes de finalizar a venda  
- Baixa automática do estoque se houver quantidade suficiente  
- Rollback automático se estoque insuficiente  

---

## 🛠️ Requisitos de Implementação

- Java 17+  
- Spring Boot 3.x  
- Maven ou Gradle  
- Banco de dados MySQL (ou H2 para testes)  
- JPA/Hibernate  
- Lombok  
- Jackson para JSON  

---

## 📹 Demonstração da API (Vídeo)

**Link:** https://drive.google.com/file/d/1qADKWoBH8dfSI3wuHcRePTJFfuBbE_34/view?usp=drivesdk

**Conteúdo do vídeo:**
1. 🎬 Apresentação facial do aluno  
2. 👤 Criação de um novo cliente  
3. 🍎 Consulta do estoque de um produto antes da venda  
4. 🛒 Registro de uma nova venda (baixa correta)  
5. ❌ Tentativa de venda com estoque insuficiente (rollback e mensagem de erro)  
6. 🔎 Consulta das novas entidades (Cliente e Venda) pelos endpoints

---

## 🚀 Inicialização do Projeto

### 1️⃣ Clonar o repositório
```bash
git clone (https://github.com/stefanynovais/ControleEstoque_StefanyNovais.git)

