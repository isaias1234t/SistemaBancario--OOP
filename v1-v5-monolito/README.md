# 🏦 Sistema Bancário API (V5)

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-darkgreen?logo=springsecurity)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-blue?logo=postgresql)](https://neon.tech/)
[![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)](https://www.docker.com/)
[![Render](https://img.shields.io/badge/Deploy-Render-black?logo=render)](https://render.com/)
[![JUnit5](https://img.shields.io/badge/Tests-JUnit5%20%2B%20Mockito-green?logo=junit5)](https://junit.org/junit5/)
[![Prometheus](https://img.shields.io/badge/Metrics-Prometheus-orange?logo=prometheus)](https://prometheus.io/)
[![Grafana](https://img.shields.io/badge/Dashboard-Grafana-orange?logo=grafana)](https://grafana.com/)
[![Zipkin](https://img.shields.io/badge/Tracing-Zipkin-yellow)](https://zipkin.io/)
[![Kafka](https://img.shields.io/badge/Messaging-Apache_Kafka-black?logo=apachekafka)](https://kafka.apache.org/)

API REST de um sistema bancário desenvolvida com **Java 21 + Spring Boot 3**, evoluída em múltiplas versões com foco em boas práticas, qualidade e arquitetura. A V5 adiciona **arquitetura orientada a eventos com Apache Kafka** — as três operações financeiras agora publicam eventos de domínio processados de forma assíncrona.

---

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
| Mensageria | Apache Kafka (KRaft mode) |
| Testes | JUnit 5 + Mockito + H2 (ambiente isolado) |
| Documentação | Swagger UI / OpenAPI 3 |
| Logs | Logback + Logstash Encoder (JSON estruturado) |
| Métricas | Micrometer + Prometheus + Grafana |
| Tracing | OpenTelemetry + Zipkin |
| Containerização | Docker + Docker Compose |
| Deploy | Render |
| Gerenciador | Maven |

---

## 📨 Arquitetura Event-Driven (V5)

A V5 introduz eventos de domínio imutáveis para as três operações financeiras. Cada operação persiste no banco de dados e, em seguida, publica um evento no Kafka para processamento assíncrono.

### Eventos de Domínio (Java Records)

| Evento | Tópico Kafka |
|---|---|
| `DepositoRealizadoEvent` | `deposito-realizado` |
| `SaqueRealizadoEvent` | `saque-realizado` |
| `TransferenciaRealizadaEvent` | `transferencia-realizada` |

Eventos modelados como `record` Java — imutáveis por natureza, pois representam **fatos ocorridos no passado**.

### Fluxo de um evento

```
HTTP Request
    │
ContaService.depositar()
    ├── persiste no banco (PostgreSQL)
    └── DepositoEventProducer
            │
            └── Kafka (tópico: deposito-realizado)
                    │
                    └── DepositoEventConsumer
                            └── log estruturado JSON
```

> A operação financeira é sempre **persistida antes** de publicar o evento. Se o banco falhar, nenhum evento espúrio é publicado.

### Kafka em KRaft Mode

Kafka rodando sem Zookeeper via Docker Compose — padrão moderno recomendado desde o Kafka 3.x.

```bash
docker-compose up kafka
```

---

## 🔭 Observabilidade (V4)

A V4 implementou os três pilares de observabilidade para aplicações em produção:

### 📋 Logs Estruturados
* Logs em formato **JSON** via Logback + Logstash Encoder.
* Cada log contém `timestamp`, `level`, `logger_name`, `traceId` e `spanId` automaticamente.
* Níveis semânticos: `INFO` para operações bem-sucedidas, `WARN` para comportamentos esperados, `ERROR` para falhas críticas.

### 📊 Métricas em Tempo Real
* **Micrometer** coleta métricas da JVM, HikariCP, HTTP e Logback automaticamente.
* **Prometheus** raspa o endpoint `/actuator/prometheus` a cada 15 segundos.
* **Grafana** exibe dashboard completo com CPU, memória heap, pool de conexões e estatísticas HTTP por endpoint.

### 🔍 Tracing Distribuído
* **OpenTelemetry** instrumenta automaticamente cada requisição com `traceId` e `spanId`.
* **Zipkin** coleta e visualiza o caminho completo de cada requisição.
* 100% das requisições rastreadas em desenvolvimento (`sampling.probability=1.0`).

### ▶️ Subindo o stack completo

```bash
docker-compose up -d
```

| Serviço | URL |
|---|---|
| Kafka | localhost:9092 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |
| Zipkin | http://localhost:9411 |

> Grafana: usuário `admin`, senha `admin`. Importe o dashboard **ID 19004**.

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

### 💳 Contas Bancárias
* Abertura de **Conta Corrente** (com limite de cheque especial).
* Abertura de **Conta Poupança** (com taxa de rendimento).
* Herança JPA `SINGLE_TABLE` na tabela `contas` com discriminador por tipo.
* Emissão de **extrato bancário** com histórico de transações persistido.

### 💰 Operações Financeiras
* **Depósito:** Incrementa o saldo da conta informada.
* **Saque:** Deduz o valor do saldo — ContaCorrente respeita o limite, ContaPoupança respeita apenas o saldo.
* **Transferência:** Movimentação segura entre contas distintas com validação de saldo.
* Valores monetários modelados com **BigDecimal** — sem perda de precisão em operações financeiras.

---

## 🧱 Arquitetura do Projeto

```
HTTP Request
    │
JwtFilter ──> SecurityContextHolder
    │
Controller ──> Service ──> Repository ──> PostgreSQL (Neon)
                  │
                  ├── EventProducer ──> Kafka ──> EventConsumer
                  │
                  └── Exception Handler (Global)

Micrometer ──> Prometheus ──> Grafana
OpenTelemetry ──> Zipkin
Logback ──> JSON estruturado
```

### Estrutura de pacotes

```
banco_api/
├── controller/       # AuthController, PessoaController, ContaController
├── service/          # AuthService, PessoaService, ContaService, JwtService
├── repository/       # PessoaRepository, ContaRepository, UsuarioRepository
├── model/            # Pessoa, PessoaFisica, PessoaJuridica, ContaBancaria...
├── dto/              # DTOs de entrada para cada endpoint
├── exception/        # Exceptions customizadas
├── security/         # SecurityConfig
├── events/           # Eventos, Producers e Consumers Kafka
├── JwtFilter.java
└── BancoApiApplication.java

docker/
└── prometheus.yml
docker-compose.yml    # Kafka + Prometheus + Grafana + Zipkin
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

### Observabilidade (público)
* `GET /actuator/health` — Status da aplicação
* `GET /actuator/prometheus` — Métricas para scraping

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
* Docker + Docker Compose
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

### 3. Subir o stack completo
```bash
docker-compose up -d
```

### 4. Rodar a aplicação
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080/swagger-ui/index.html`.

### 5. Rodar os testes
```bash
./mvnw test
```

> Os testes rodam com H2 em memória via `@ActiveProfiles("test")` — sem dependência de infraestrutura externa.

---

## 🧪 Cobertura de Testes

| Classe | Testes | Cenários |
|---|---|---|
| `PessoaService` | 4 | Criar PF, Criar PJ, Buscar com sucesso, Buscar não encontrado |
| `ContaService` | 6 | Criar corrente/poupança (sucesso e falha), Buscar conta (sucesso e falha) |
| `AuthService` | 5 | Registrar, Login com sucesso, Email não encontrado, Senha incorreta, Token gerado |
| **Total** | **15** | |

---

## 📈 Evolução do Projeto

| Versão | Descrição |
|---|---|
| V1 | POO pura em Java — sem framework |
| V2 | API REST com Spring Boot — dados em memória |
| V3 | JPA + PostgreSQL + JWT + Deploy no Render |
| V4 | Observabilidade completa: Logs JSON + Prometheus/Grafana + Zipkin |
| **V5** | **Event-Driven: Apache Kafka + Eventos de domínio imutáveis (records)** |
| V6 *(planejada)* | Microsserviços com Spring Cloud |

---

## 👨‍💻 Autor

**Isaías Rodrigues de Almeida**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/in/isaías-rodrigues-2156982a6/)
[![GitHub](https://img.shields.io/badge/GitHub-black?logo=github)](https://github.com/isaias1234t)

Desenvolvido com o propósito de consolidar conhecimentos em Desenvolvimento Backend Java, APIs RESTful, Segurança com JWT, Persistência com JPA/Hibernate, mensageria com Apache Kafka e boas práticas de testes e observabilidade em produção.