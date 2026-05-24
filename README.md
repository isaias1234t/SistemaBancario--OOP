# Sistema Bancário API

API bancária desenvolvida com Java e Spring Boot para gerenciamento de clientes, contas correntes, contas poupança e operações bancárias.

## Sobre o Projeto

Este projeto começou como um sistema bancário utilizando Programação Orientada a Objetos em Java puro (V1.0) e evoluiu para uma API REST completa utilizando Spring Boot (V2.0).

A aplicação simula operações reais de um sistema bancário, incluindo criação de clientes, gerenciamento de contas, depósitos, saques, transferências e geração de extrato.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Maven
- Swagger / OpenAPI
- REST API
- Programação Orientada a Objetos
- IntelliJ IDEA

## Funcionalidades

### Clientes
- Criar cliente
- Listar clientes

### Contas
- Criar conta corrente
- Criar conta poupança
- Listar contas
- Consultar extrato

### Operações Bancárias
- Depósito
- Saque
- Transferência entre contas

### Regras de Negócio

- Controle de saldo
- Controle de limite em conta corrente
- Histórico de transações
- Tratamento de exceções
- Retornos HTTP apropriados

## Estrutura da API

### Cliente Controller

- `GET /clientes`
- `POST /clientes`

### Conta Controller

- `GET /contas`
- `GET /contas/{id}/extrato`
- `POST /contas/corrente`
- `POST /contas/poupanca`
- `POST /contas/{id}/deposito`
- `POST /contas/{id}/saque`
- `POST /contas/{id}/transferencia`

## Swagger/OpenAPI

Documentação automática disponível em:

```bash
http://localhost:8080/swagger-ui/index.html
```

Após deploy:

```bash
https://SEUAPP.onrender.com/swagger-ui/index.html
```

## Como Executar o Projeto

### Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/Sistema-Bancario-OOP.git
```

### Entrar na pasta do projeto

```bash
cd Sistema-Bancario-OOP
```

### Executar com Maven

```bash
mvn spring-boot:run
```

## Build do Projeto

```bash
mvn clean package
```

O arquivo `.jar` será gerado na pasta:

```bash
target/
```

## Objetivos do Projeto

- Praticar Programação Orientada a Objetos
- Aplicar conceitos de APIs REST
- Utilizar Spring Boot na prática
- Implementar regras de negócio reais
- Trabalhar com arquitetura em camadas
- Aprender serialização JSON e DTOs
- Documentar APIs com Swagger

## Próximas Melhorias (V3)

- DTOs completos
- Banco de dados PostgreSQL
- Spring Data JPA
- Validações com Bean Validation
- Autenticação com Spring Security
- Deploy com Docker
- Testes automatizados

## Autor

Isaías Rodrigues de Almeida
