# 🏦 Sistema Bancário API (V2)

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)](https://www.docker.com/)
[![Render](https://img.shields.io/badge/Deploy-Render-black?logo=render)](https://render.com/)

API REST de um sistema bancário desenvolvida com **Java + Spring Boot**. O projeto representa a evolução de uma aplicação puramente baseada em Programação Orientada a Objetos (V1) para uma arquitetura moderna, escalável e baseada em APIs REST (V2).

A aplicação simula o ecossistema de um banco real, gerenciando clientes, contas e operações financeiras com validações robustas.

---

## 🌐 API em Produção

* **Link da API:** [https://sistemabancario-oop-1-32l5.onrender.com/](https://sistemabancario-oop-1-32l5.onrender.com/)
* **Documentação Interativa (Swagger):** [Acessar Interface Swagger](https://sistemabancario-oop-1-32l5.onrender.com/swagger-ui/index.html)

---

## 🚀 Tecnologias e Ferramentas

* **Linguagem:** Java 21
* **Framework:** Spring Boot (Spring Web, Spring Validation)
* **Gerenciador de Dependências:** Maven
* **Documentação:** Swagger UI / OpenAPI 3
* **Containerização:** Docker
* **Hospedagem/Cloud:** Render

---

## ⚙️ Funcionalidades & Regras de Negócio

### 👤 Clientes
* Cadastro de novos clientes com validação de dados.
* Listagem completa de clientes cadastrados.

### 💳 Contas Bancárias
* Abertura de **Conta Corrente** (com limite de cheque especial).
* Abertura de **Conta Poupança**.
* Consulta de saldo e listagem de contas.
* Emissão de **extrato bancário** detalhado com histórico de transações.

### 💰 Operações Financeiras
* **Depósito:** Incrementa o saldo da conta informada.
* **Saque:** Deduz o valor do saldo (respeitando o limite disponível para conta corrente).
* **Transferência:** Movimentação de valores entre contas distintas de forma segura.

### 📌 Diferenciais Implementados
* Tratamento global de exceções (`@RestControllerAdvice`).
* Respostas HTTP padronizadas e semânticas.
* Validação de campos obrigatórios e formatos com `Spring Validation`.

---

## 🧱 Arquitetura do Projeto

O projeto adota o padrão de arquitetura em camadas para garantir a separação de responsabilidades:

Controller ──> Service ──> Model (Entidades/DTOs)
└── Exception Handler (Global)


---

## 📖 Endpoints Principais

### Clientes
* `GET /clientes` - Lista todos os clientes.
* `POST /clientes` - Cria um novo cliente.

### Contas
* `GET /contas` - Lista todas as contas.
* `POST /contas/corrente` - Abre uma conta corrente.
* `POST /contas/poupanca` - Abre uma conta poupança.
* `GET /contas/{id}/extrato` - Retorna o histórico e saldo da conta.

### Operações
* `POST /contas/{id}/deposito` - Efetua depósito em uma conta.
* `POST /contas/{id}/saque` - Efetua saque de uma conta.
* `POST /contas/{id}/transferencia` - Realiza transferência para outra conta.

---

▶️ Como Executar Localmente
Pré-requisitos
Java 21 instalado.

Maven instalado (ou utilize o wrapper ./mvnw).

1. Clonar o repositório
   Bash
   git clone https://github.com/isaias1234t/SistemaBancario--OOP.git
   cd SistemaBancario--OOP
2. Rodar a aplicação
   Bash
   ./mvnw spring-boot:run
   A API estará disponível em http://localhost:8080.

3. Gerar o Build (Opcional)
   Para gerar o arquivo .jar executável:

No Bash:
   ./mvnw clean package

O arquivo será gerado na pasta /target.

🚀 Deploy e Infraestrutura
O deploy da aplicação é feito de forma automatizada na plataforma Render, utilizando um container Docker focado no ambiente de produção do Spring Boot.

📈 Próximas Melhorias (Roadmap)
[ ] Integração com Banco de Dados Relacional (PostgreSQL)

[ ] Implementação do Spring Data JPA para persistência

[ ] Segurança com Spring Security & JWT (Autenticação/Autorização)

[ ] Cobertura de testes automatizados (JUnit 5 e Mockito)

[ ] Orquestração local com Docker Compose

[ ] Pipeline de CI/CD automatizada (GitHub Actions)

👨‍💻 Autor:
Isaías Rodrigues de Almeida

Desenvolvido com o propósito de consolidar conhecimentos em Desenvolvimento Backend Java, APIs RESTful, Arquitetura em Camadas e Cloud Deploy.
