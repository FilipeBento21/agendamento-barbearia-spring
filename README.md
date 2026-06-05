# 💈 API de Agendamento de Barbearia

Projeto desenvolvido para a disciplina de **Programação Cliente-Servidor com Spring Boot (UNIBRA 2026.1)**.

A aplicação consiste em uma **API REST** para gerenciamento de uma barbearia, permitindo o cadastro de clientes, barbeiros, serviços e agendamentos.

---

## 🚀 Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven
* H2 / MySQL
* Swagger (OpenAPI)
* Lombok
* Jakarta Validation
* JaCoCo

---

## 📁 Estrutura do Projeto

```
src/main/java/com/filipe_bento/agendamento_barbearia/

├── controller/        → Camada REST (entrada/saída HTTP)
├── service/           → Regras de negócio
├── repository/        → Acesso ao banco de dados
├── entity/            → Entidades JPA
├── dto/               → RequestDTO e ResponseDTO
├── exception/         → Tratamento global de erros
├── config/            → Configurações (Swagger, Segurança, etc)
```

---

## 🧠 Arquitetura

A aplicação segue o padrão **MVC + Service Layer**:

```
Controller → DTO → Service → Repository → Banco de Dados
```

### 📌 Responsabilidades

* **Controller** → Recebe requisições HTTP
* **DTO** → Controla entrada e saída de dados
* **Service** → Contém regras de negócio
* **Repository** → Comunicação com banco de dados

---

## 🔐 Segurança da Informação Aplicada

O projeto implementa diversas boas práticas de segurança recomendadas para APIs REST:

### 🧱 Padrão DTO (Data Transfer Object)

Evita:

* Mass Assignment
* Over-Posting
  Garante que entidades não sejam expostas diretamente.

---

### 🛡️ Validação de Entrada (Jakarta Validation)

Uso de:

* `@NotBlank`
* `@NotNull`
* `@Size`

Previne:

* Dados inválidos
* Injeções
* Erros de integridade

---

### 🚫 Ocultação de Stack Trace

Uso de:

* `GlobalExceptionHandler`
* `ResourceExceptionHandler`

Evita exposição de:

* Estrutura interna
* Dados sensíveis
* Erros do servidor

---

### 🧼 Sanitização de Payloads

Tratamento de:

* `HttpMessageNotReadableException`

Protege contra:

* JSON inválido
* Quebra do parser
* Dados maliciosos

---

### 🔐 Spring Security

Proteção inicial com:

* Autenticação em memória

---

### 🚀 Melhorias Futuras de Segurança

* JWT (JSON Web Token)
* Controle de acesso por roles
* Logs de auditoria
* Criptografia

---

## 🔄 Regras de Negócio

* Cliente deve possuir **nome, email e telefone válidos**
* Email do cliente deve ser **único**
* Agendamento deve possuir:

  * Cliente existente
  * Barbeiro existente
  * Pelo menos 1 serviço
* Serviços possuem preço obrigatório
* Barbeiros podem ter especialidade

---

## ⚠️ Tratamento de Erros

### 🔴 404 - Recurso não encontrado

```json
{
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "Cliente não encontrado com o ID: 1",
  "path": "/api/v1/clientes/1"
}
```

### 🟡 400 - Erro de validação

```json
{
  "status": 400,
  "error": "Erro de validação",
  "message": "Um ou mais campos estão inválidos.",
  "errors": [
    {
      "fieldName": "nome",
      "message": "Nome é obrigatório"
    }
  ]
}
```

---

## 📌 Endpoints Principais

### 👤 Clientes

| Método | Endpoint                     | Descrição       |
| ------ | ---------------------------- | --------------- |
| GET    | /api/v1/clientes             | Listar          |
| GET    | /api/v1/clientes/{id}        | Buscar por ID   |
| GET    | /api/v1/clientes/busca?nome= | Buscar por nome |
| POST   | /api/v1/clientes             | Criar           |
| PUT    | /api/v1/clientes/{id}        | Atualizar       |
| DELETE | /api/v1/clientes/{id}        | Remover         |

---

### ✂️ Barbeiros

| Método | Endpoint               |
| ------ | ---------------------- |
| GET    | /api/v1/barbeiros      |
| POST   | /api/v1/barbeiros      |
| PUT    | /api/v1/barbeiros/{id} |
| DELETE | /api/v1/barbeiros/{id} |

---

### 💇 Serviços

| Método | Endpoint              |
| ------ | --------------------- |
| GET    | /api/v1/servicos      |
| POST   | /api/v1/servicos      |
| PUT    | /api/v1/servicos/{id} |
| DELETE | /api/v1/servicos/{id} |

---

### 📅 Agendamentos

| Método | Endpoint                  |
| ------ | ------------------------- |
| GET    | /api/v1/agendamentos      |
| POST   | /api/v1/agendamentos      |
| PUT    | /api/v1/agendamentos/{id} |
| DELETE | /api/v1/agendamentos/{id} |

---

## 📥 Exemplos de Requisição

### Criar Cliente

```bash
curl -X POST http://localhost:8080/api/v1/clientes \
-H "Content-Type: application/json" \
-d '{
  "nome": "Filipe",
  "telefone": "99999-9999",
  "email": "filipe@email.com"
}'
```

---

## 🧪 Testes

Estratégia baseada na **Pirâmide de Testes**:

* Repository → `@DataJpaTest`
* Service → Mockito
* Controller → `@WebMvcTest`

### ▶️ Executar testes

```bash
./mvnw test
```

---

## 📊 Cobertura de Código (JaCoCo)

### ▶️ Gerar relatório

```bash
./mvnw clean verify
```

### 📂 Acessar relatório

```
target/site/jacoco/index.html
```

🎯 Meta: **≥ 70%**

---

## 📘 Documentação Swagger

Acesse:

```
http://localhost:8080/swagger-ui.html
```

Permite:

* Visualizar endpoints
* Testar API diretamente
* Ver contratos de requisição/resposta

---

## ⚙️ Como Rodar o Projeto

### 1. Clonar repositório

```bash
git clone <URL>
```

### 2. Configurar banco

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/barbearia
spring.datasource.username=root
spring.datasource.password=123456
```

### 3. Rodar aplicação

```bash
./mvnw spring-boot:run
```

---

## 👨‍💻 Autores

* Filipe Bento
* Hytalo Leão

---

## 🎯 Status do Projeto

✔ CRUD completo
✔ DTOs implementados
✔ Validações
✔ Tratamento de erros
✔ Swagger documentado
✔ Testes implementados
✔ Segurança básica aplicada

---

## 🚀 Próximas Melhorias

* Regras avançadas de agendamento (horários)
* Autenticação com JWT
* Paginação
* Logs estruturados
* Deploy em nuvem

---

## 📌 Conclusão

O projeto implementa uma API REST completa, seguindo boas práticas de arquitetura, segurança e organização de código, estando preparado para evolução e uso em cenários reais.
