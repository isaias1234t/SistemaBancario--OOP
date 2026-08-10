# 🏦 Sistema Bancário — Evolução Arquitetural

[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-Microservices-blue?logo=spring)](https://spring.io/projects/spring-cloud)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-blue?logo=postgresql)](https://neon.tech/)
[![Apache Kafka](https://img.shields.io/badge/Messaging-Apache_Kafka-black?logo=apachekafka)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)](https://www.docker.com/)
[![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub_Actions-black?logo=githubactions)](https://github.com/isaias1234t/SistemaBancario--OOP/actions)

Repositório único que documenta a **evolução completa de um sistema bancário**, do zero absoluto até uma arquitetura de microsserviços com Spring Cloud.

Cada versão resolve um problema real de engenharia — não é refatoração por estética, é evolução motivada por limitações concretas da versão anterior.

---

## 📂 Estrutura do Repositório

```
SistemaBancario--OOP/
├── v1-v5-monolito/     # Evolução monolítica (POO pura → Event-Driven)
└── v6-microservices/   # Arquitetura de microsserviços com Spring Cloud
```

---

## 📈 Evolução do Projeto

| Versão | Abordagem | Problema resolvido |
|---|---|---|
| **V1** | POO pura em Java | Modelagem orientada a objetos sem framework |
| **V2** | API REST com Spring Boot | Dados em memória, primeiros endpoints REST |
| **V3** | JPA + PostgreSQL + JWT | Persistência real + autenticação stateless |
| **V4** | Observabilidade completa | Logs JSON + Prometheus/Grafana + Zipkin |
| **V5** | Event-Driven com Kafka | Operações financeiras publicam eventos de domínio |
| **V6** | Microsserviços + Spring Cloud | Separação de responsabilidades, escalabilidade independente |
| **V6.1** | CI/CD com GitHub Actions | Pipeline seletivo por serviço — detect → build → test |

---

## 🗂️ Versões

### V1 → V5 — Monolito em Evolução

> 📁 [`v1-v5-monolito/`](./v1-v5-monolito)

A jornada começa com POO pura — sem Spring, sem banco, sem framework. A cada versão, uma nova camada de complexidade real é adicionada até chegar em um monolito event-driven com Kafka, observabilidade com Prometheus/Grafana/Zipkin e deploy no Render.

➡️ [README completo do monolito](./v1-v5-monolito/README.md)

**API em produção (V5):**
[sistemabancario-oop-1-32l5.onrender.com/swagger-ui/index.html](https://sistemabancario-oop-1-32l5.onrender.com/swagger-ui/index.html)

> ⚠️ Hospedado no plano gratuito do Render — a primeira requisição pode demorar até 30 segundos.

---

### V6 — Microsserviços com Spring Cloud

> 📁 [`v6-microservices/`](./v6-microservices)

A V6 desmembra o monolito em microsserviços independentes, cada um com seu próprio banco de dados, ciclo de deploy e responsabilidade de negócio. Toda a infraestrutura sobe com um único comando.

➡️ [README completo do V6](./v6-microservices/README.md)

```bash
cd v6-microservices
cp .env.example .env   # preencha com suas credenciais
docker compose up --build
```

---

## 👨‍💻 Autor

**Isaías Rodrigues de Almeida**

Estudante de Desenvolvimento de Software Multiplataforma na FATEC Franca. Este repositório é o registro público da minha evolução como desenvolvedor backend Java.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/in/isaías-rodrigues-2156982a6/)
[![GitHub](https://img.shields.io/badge/GitHub-black?logo=github)](https://github.com/isaias1234t)