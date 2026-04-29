# 💰 Wallet API

Uma API RESTful robusta desenvolvida para gerir carteiras digitais, permitindo o controlo de saldo, depósitos, levantamentos e transferências entre utilizadores de forma segura e eficiente.

---

## 🚀 Tecnologias

Este projeto foi construído utilizando as seguintes tecnologias:

* **Java 17+**
* **Spring Boot 3**
* **Spring Data JPA**
* **Spring Security (JWT)**
* **PostgreSQL**
* **Maven**

---

## 📋 Funcionalidades

* **Gestão de Utilizadores:** Registo e autenticação segura.
* **Carteira Digital:** Criação automática de carteira associada ao utilizador.
* **Transações:**
    * Depósitos e levantamentos.
    * Transferências entre utilizadores com validação de saldo em tempo real.
    * Consulta de histórico/extrato.
* **Segurança:** Proteção de rotas sensíveis via tokens JWT.

---

## ⚙️ Como Executar

### Pré-requisitos
* JDK 17 ou superior
* Maven instalado
* PostgreSQL ativo (local ou via Docker)

### Passos para Instalação

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/rauljanuario/wallet-api.git](https://github.com/rauljanuario/wallet-api.git)
    cd wallet-api
    ```

2.  **Configure o banco de dados:**
    Edite o ficheiro `src/main/resources/application.properties` com as suas credenciais:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/wallet_db
    spring.datasource.username=seu_utilizador
    spring.datasource.password=sua_senha
    ```

3.  **Execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```
    A API estará disponível em `http://localhost:8080`.

---

## 🛣️ Endpoints Principais (Exemplos)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/login` | Autentica e devolve o token JWT. |
| `GET` | `/accounts/me/info` | Mostra informações do usuários. |
| `POST` | `/transactions` | Realiza transferência para outro utilizador. |

---

## 🧠 Decisões de Engenharia

* **Integridade de Dados:** A lógica de transferência utiliza transações SQL para garantir que o dinheiro saia de uma conta e entre na outra de forma atómica.
* **Arquitetura:** Organizado seguindo o padrão de camadas (Controller, Service, Repository), facilitando a manutenção e a criação de testes.
* **Segurança:** Implementação de autenticação *stateless* com JWT, garantindo que apenas utilizadores autorizados acedam aos seus próprios dados financeiros.

---
Desenvolvido por [Raul Januario](www.linkedin.com/in/rauljanuario)
