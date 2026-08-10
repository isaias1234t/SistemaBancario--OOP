# 🏦 Sistema Bancário V6 — Microsserviços

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-Eureka%20%2B%20Gateway-blue?logo=spring)](https://spring.io/projects/spring-cloud)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-blue?logo=postgresql)](https://neon.tech/)
[![Apache Kafka](https://img.shields.io/badge/Messaging-Kafka_KRaft-black?logo=apachekafka)](https://kafka.apache.org/)
[![Flyway](https://img.shields.io/badge/Migrations-Flyway-red?logo=flyway)](https://flywaydb.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)](https://www.docker.com/)
[![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub_Actions-black?logo=githubactions)](https://github.com/isaias1234t/SistemaBancario--OOP/actions)

A V6 desmembra o monolito em microsserviços independentes com Spring Cloud. Cada serviço tem seu próprio banco de dados, ciclo de vida e responsabilidade de negócio. Toda a arquitetura sobe com um único comando Docker Compose.

---

## 🏗️ Arquitetura

```
Cliente HTTP
     │
     ▼
┌─────────────┐
│  API Gateway │  :8080  — roteamento centralizado
└──────┬──────┘
       │  (via Eureka)
       ├──────────────────────┐──────────────────────┐
       ▼                      ▼                      ▼
┌─────────────┐      ┌───────────────┐      ┌───────────────┐
│ auth-service│      │cliente-service│      │ conta-service │
│    :8082    │      │    :8081      │      │    :8083      │
└──────┬──────┘      └──────┬────────┘      └──────┬────────┘
       │                    │                       │
       ▼                    ▼                       ▼
  PostgreSQL           PostgreSQL              PostgreSQL
   (Neon)               (Neon)                 (Neon)
                                                    │
                                                    ▼
                                              Apache Kafka
                                               (KRaft)

┌───────────────┐
│ eureka-server │  :8761  — service discovery
└───────────────┘
```

---

## 🚀 Microsserviços

| Serviço | Porta | Responsabilidade |
|---|---|---|
| `eureka-server` | 8761 | Service discovery — registro e localização dos serviços |
| `api-gateway` | 8080 | Ponto de entrada único — roteamento via Eureka |
| `auth-service` | 8082 | Autenticação JWT — registro e login de usuários |
| `cliente-service` | 8081 | Cadastro de Pessoa Física e Jurídica |
| `conta-service` | 8083 | Contas bancárias, operações financeiras e eventos Kafka |

> O `config-server` está presente no repositório mas reservado para uma versão futura. Cada serviço gerencia seu próprio `application.properties`.

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.4 |
| Microsserviços | Spring Cloud (Eureka + Gateway) |
| Persistência | Spring Data JPA + Hibernate |
| Banco de Dados | PostgreSQL (Neon — Cloud, um banco por serviço) |
| Autenticação | Spring Security + JWT |
| Mensageria | Apache Kafka (KRaft — sem Zookeeper) |
| Migrações | Flyway |
| Containerização | Docker + Docker Compose |
| Build | Maven (Multi-stage Docker build) |
| CI/CD | GitHub Actions (pipeline seletivo por serviço) |

---

## 📨 Eventos de Domínio (Kafka)

O `conta-service` publica eventos imutáveis modelados como Java Records após cada operação financeira.

| Evento | Tópico |
|---|---|
| `DepositoRealizadoEvent` | `deposito-realizado` |
| `SaqueRealizadoEvent` | `saque-realizado` |
| `TransferenciaRealizadaEvent` | `transferencia-realizada` |

A operação é **sempre persistida antes** de publicar o evento. Se o banco falhar, nenhum evento espúrio é publicado.

---

## 🔒 Concorrência

O `conta-service` implementa controle de concorrência robusto para operações financeiras simultâneas:

- `@Transactional` em todas as operações
- `PESSIMISTIC_WRITE` lock no banco
- Ordenação de locks para eliminar deadlock em transferências cruzadas
- `BigDecimal` em todos os valores monetários

**Testes de concorrência implementados e aprovados:**

| Teste | Threads | Cenário |
|---|---|---|
| Depósitos concorrentes | 20 | 20 depósitos simultâneos na mesma conta |
| Saques concorrentes | 20 | 20 saques simultâneos com validação de saldo |
| Transferências concorrentes | 20 | 20 transferências simultâneas A → B |
| Anti-deadlock | 20 | 10 transferências A→B + 10 B→A simultaneamente |

O teste anti-deadlock valida o invariante do sistema: `saldo(A) + saldo(B) = total inicial`, independentemente da ordem de execução.

---

## ▶️ Como Executar

### Pré-requisitos

- Docker + Docker Compose
- Contas no [Neon](https://neon.tech) (3 bancos — um por serviço de negócio)

### 1. Clonar o repositório

```bash
git clone https://github.com/isaias1234t/SistemaBancario--OOP.git
cd SistemaBancario--OOP/v6-microservices
```

### 2. Configurar variáveis de ambiente

```bash
cp .env.example .env
```

Preencha o `.env` com suas credenciais:

```env
# Eureka
EUREKA_URL=http://eureka-server:8761/eureka

# Kafka
KAFKA_BOOTSTRAP_SERVERS=kafka:9092

# auth-service
AUTH_DATABASE_URL=jdbc:postgresql://seu-host.neon.tech/auth-db?sslmode=require
AUTH_DATABASE_USER=seu-usuario
AUTH_DATABASE_PASSWORD=sua-senha
JWT_SECRET=sua-chave-secreta-minimo-32-caracteres
JWT_EXPIRATION=86400000

# cliente-service
CLIENTE_DATABASE_URL=jdbc:postgresql://seu-host.neon.tech/cliente-db?sslmode=require
CLIENTE_DATABASE_USER=seu-usuario
CLIENTE_DATABASE_PASSWORD=sua-senha

# conta-service
CONTA_DATABASE_URL=jdbc:postgresql://seu-host.neon.tech/conta-db?sslmode=require
CONTA_DATABASE_USER=seu-usuario
CONTA_DATABASE_PASSWORD=sua-senha
```

### 3. Subir toda a arquitetura

```bash
docker compose up --build
```

Serviços disponíveis após a inicialização:

| Serviço | URL |
|---|---|
| API Gateway | http://localhost:8080 |
| Eureka Dashboard | http://localhost:8761 |
| auth-service | http://localhost:8082 |
| cliente-service | http://localhost:8081 |
| conta-service | http://localhost:8083 |

---

## 📖 Endpoints Principais

### Auth (público)
- `POST /auth/registro` — Registra novo usuário
- `POST /auth/login` — Retorna token JWT

### Clientes (requer token)
- `POST /clientes/fisica` — Cadastra Pessoa Física
- `POST /clientes/juridica` — Cadastra Pessoa Jurídica
- `GET /clientes/{id}` — Busca cliente por ID

### Contas (requer token)
- `POST /contas/corrente` — Abre Conta Corrente
- `POST /contas/poupanca` — Abre Conta Poupança
- `GET /contas/{id}/extrato` — Extrato da conta

### Operações (requer token)
- `POST /contas/{id}/deposito` — Efetua depósito
- `POST /contas/{id}/saque` — Efetua saque
- `POST /contas/{id}/transferencia` — Realiza transferência

---

## 📁 Estrutura do Projeto

```
v6-microservices/
├── docker-compose.yml
├── .env.example
├── eureka-server/
│   ├── Dockerfile
│   └── src/
├── api-gateway/
│   ├── Dockerfile
│   └── src/
├── auth-service/
│   ├── Dockerfile
│   └── src/
├── cliente-service/
│   ├── Dockerfile
│   └── src/
└── conta-service/
    ├── Dockerfile
    └── src/
```

---

## ⚙️ CI/CD

O pipeline de integração contínua roda automaticamente no GitHub Actions a cada push em `v6-microservices/**`.

**Estratégia:** detecção seletiva por serviço — só reconstrói e testa o que realmente mudou.

```
detect → build → test
```

| Job | O que faz |
|---|---|
| `detect` | Identifica quais serviços foram alterados no push (`dorny/paths-filter`) |
| `build` | Compila apenas os serviços alterados (`mvn package -DskipTests`) |
| `test` | Executa os testes unitários dos serviços alterados (`mvn test -Dgroups='!integration'`) |

> Testes de integração são excluídos do pipeline por dependerem de infraestrutura externa (Neon, Kafka).

---

## 👨‍💻 Autor

**Isaías Rodrigues de Almeida**

Estudante de Desenvolvimento de Software Multiplataforma na FATEC Franca.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/in/isaías-rodrigues-2156982a6/)
[![GitHub](https://img.shields.io/badge/GitHub-black?logo=github)](https://github.com/isaias1234t)