# 🏦 Sistema Bancário API (V3)

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-darkgreen?logo=springsecurity)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-blue?logo=postgresql)](https://neon.tech/)
[![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)](https://www.docker.com/)
[![Render](https://img.shields.io/badge/Deploy-Render-black?logo=render)](https://render.com/)
[![JUnit5](https://img.shields.io/badge/Tests-JUnit5%20%2B%20Mockito-green?logo=junit5)](https://junit.org/junit5/)

API REST de um sistema bancário desenvolvida com **Java 21 + Spring Boot 3**. A V3 representa a evolução completa da aplicação, adicionando persistência real com **JPA/Hibernate + PostgreSQL**, autenticação segura com **Spring Security + JWT** e cobertura de testes com **JUnit 5 + Mockito**.
 

## 🌐 API em Produção

* **Documentação Interativa (Swagger):** [Acessar Interface Swagger](https://sistemabancario-oop-1-32l5.onrender.com/swagger-ui/index.html)

> ⚠️ O serviço está hospedado no plano gratuito do Render. A primeira requisição pode demorar até 30 segundos para a instância inicializar.

---

## 🚀 Tecnologias e Ferramentas

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3 (Web, Validation, Security) |
| Persistência | JPA / Hibernate + Spring Data JPA |
| Banco de Dados | PostgreSQL (Neon — Cloud) |
| Autenticação | Spring Security + JSON Web Token (JWT) |
| Testes | JUnit 5 + Mockito |
| Documentação | Swagger UI / OpenAPI 3 |
| Containerização | Docker |
| Deploy | Render |
| Gerenciador | Maven |

---

## ⚙️ Funcionalidades & Regras de Negócio

### 🔐 Autenticação
* Registro de usuários com senha criptografada via **BCryptPasswordEncoder**.
* Login com retorno de **token JWT** — autenticação stateless.
* Rotas `/auth/**` públicas; todos os demais endpoints exigem token válido.
* Controle de acesso por roles: `USER` e `ADMIN`.

### 👤 Pessoas
* Cadastro de **Pessoa Física** (nome + CPF) e **Pessoa Jurídica** (razão social + CNPJ).
* Modelagem com herança JPA `SINGLE_TABLE` — ambos os tipos persistidos na tabela `pessoas`.
* Busca por ID e listagem separada por tipo.

### 💳 Contas Bancárias
* Abertura de **Conta Corrente** (com limite de cheque especial).
* Abertura de **Conta Poupança** (com taxa de rendimento).
* Herança JPA `SINGLE_TABLE` na tabela `contas` com discriminador por tipo.
* Consulta de saldo e listagem de contas.
* Emissão de **extrato bancário** com histórico de transações persistido.

### 💰 Operações Financeiras
* **Depósito:** Incrementa o saldo da conta informada.
* **Saque:** Deduz o valor do saldo — ContaCorrente respeita o limite, ContaPoupança respeita apenas o saldo.
* **Transferência:** Movimentação segura entre contas distintas com validação de saldo.

### 📌 Diferenciais Implementados
* Persistência real com JPA/Hibernate — dados sobrevivem a reinicializações.
* Tratamento global de exceções (`@RestControllerAdvice`).
* Respostas HTTP padronizadas e semânticas.
* Validação de campos com Jakarta Validation (`@NotBlank`, `@Positive`).
* 14 testes unitários cobrindo `PessoaService`, `ContaService` e `AuthService`.

---

## 🧱 Arquitetura do Projeto

```
Controller ──> Service ──> Repository ──> PostgreSQL (Neon)
     │              │
     │         Exception Handler (Global)
     │
  JwtFilter ──> SecurityContextHolder
```

### Estrutura de pacotes

```
banco_api/
├── controller/       # AuthController, PessoaController, ContaController
├── service/          # AuthService, PessoaService, ContaService, JwtService
├── repository/       # PessoaRepository, ContaRepository, UsuarioRepository...
├── model/            # Pessoa, PessoaFisica, PessoaJuridica, ContaBancaria...
├── dto/              # DTOs de entrada para cada endpoint
├── exception/        # Exceptions customizadas
├── security/         # SecurityConfig
├── JwtFilter.java    # Filtro de validação JWT por requisição
└── BancoApiApplication.java
```

---

## 📖 Endpoints Principais

### Autenticação (público)
* `POST /auth/registro` — Registra um novo usuário
* `POST /auth/login` — Realiza login e retorna o token JWT

### Pessoas (requer token)
* `GET /pessoas/fisicas` — Lista todas as pessoas físicas
* `GET /pessoas/juridicas` — Lista todas as pessoas jurídicas
* `GET /pessoas/{id}` — Busca pessoa por ID
* `POST /pessoas/fisica` — Cadastra pessoa física
* `POST /pessoas/juridica` — Cadastra pessoa jurídica

### Contas (requer token)
* `GET /contas` — Lista todas as contas
* `POST /contas/corrente` — Abre conta corrente
* `POST /contas/poupanca` — Abre conta poupança
* `GET /contas/{id}/extrato` — Retorna extrato da conta

### Operações (requer token)
* `POST /contas/{id}/deposito` — Efetua depósito
* `POST /contas/{id}/saque` — Efetua saque
* `POST /contas/{id}/transferencia` — Realiza transferência

---

## 🔑 Como usar a autenticação no Swagger

1. Acesse a [interface Swagger](https://sistemabancario-oop-1-32l5.onrender.com/swagger-ui/index.html)
2. Registre um usuário em `POST /auth/registro`
3. Faça login em `POST /auth/login` e copie o token retornado
4. Clique em **Authorize** (cadeado no topo da página)
5. Cole `Bearer seutoken` e confirme
6. Todos os endpoints estarão liberados para uso

---

## ▶️ Como Executar Localmente

### Pré-requisitos
* Java 21
* Maven
* Docker (opcional)
* Conta no [Neon](https://neon.tech) para o banco PostgreSQL

### 1. Clonar o repositório
```bash
git clone https://github.com/isaias1234t/SistemaBancario--OOP.git
cd SistemaBancario--OOP
```

### 2. Configurar variáveis de ambiente
Crie um arquivo `.env` na raiz do projeto:
```
DATABASE_URL=jdbc:postgresql://seu-host.neon.tech/banco-api?sslmode=require
JWT_SECRET=sua-chave-secreta-muito-longa-aqui-minimo-32-caracteres
```

### 3. Rodar a aplicação
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080/swagger-ui/index.html`.

### 4. Rodar via Docker
```bash
docker build -t banco-api .
docker run -p 8080:8080 \
  -e DATABASE_URL=sua_url \
  -e JWT_SECRET=sua_chave \
  banco-api
```

### 5. Rodar os testes
```bash
./mvnw test
```

---

## 🧪 Cobertura de Testes

| Classe | Testes | Cenários |
|---|---|---|
| `PessoaService` | 4 | Criar PF, Criar PJ, Buscar com sucesso, Buscar não encontrado |
| `ContaService` | 6 | Criar corrente/poupança (sucesso e falha), Buscar conta (sucesso e falha) |
| `AuthService` | 4 | Registrar, Login com sucesso, Email não encontrado, Senha incorreta |
| **Total** | **14** | |

---

## 📈 Evolução do Projeto

| Versão | Descrição |
|---|---|
| V1 | POO pura em Java — sem framework |
| V2 | API REST com Spring Boot — dados em memória |
| **V3** | **JPA + PostgreSQL + JWT + Testes — produção real** |

---

## 👨‍💻 Autor

**Isaías Rodrigues de Almeida**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/in/isaías-rodrigues-2156982a6/)
[![GitHub](https://img.shields.io/badge/GitHub-black?logo=github)](https://github.com/isaias1234t)

Desenvolvido com o propósito de consolidar conhecimentos em Desenvolvimento Backend Java, APIs RESTful, Segurança com JWT, Persistência com JPA/Hibernate e boas práticas de testes automatizados.
