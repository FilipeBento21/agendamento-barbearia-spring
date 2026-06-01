# 💈 API de Agendamento de Barbearia

Projeto desenvolvido para a disciplina de **Programação Cliente-Servidor com Spring Boot (UNIBRA 2026.1)**.

A aplicação consiste em uma API REST para gerenciamento de uma barbearia, permitindo o cadastro de clientes, barbeiros, serviços e agendamentos.

---

# 🚀 Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven
* MySQL
* Swagger (OpenAPI)
* Lombok
* Jakarta Validation

---

# 📁 Estrutura do Projeto

```
src/main/java/com/filipe_bento/agendamento_barbearia/
│
├── controller/        → Camada REST (entrada/saída HTTP)
├── service/           → Regras de negócio
├── repository/        → Acesso ao banco de dados
├── entity/            → Entidades JPA
├── dto/               → RequestDTO e ResponseDTO
├── exception/         → Tratamento global de erros
```

---

# 🧠 Arquitetura

A aplicação segue o padrão **MVC + Service Layer**:

```
Controller → DTO → Service → Repository → Banco de Dados
```

* **Controller**: recebe requisições HTTP
* **Service**: contém regras de negócio
* **Repository**: comunica com o banco
* **DTO**: controla entrada e saída de dados

---

# 🔐 Uso de DTOs

Os DTOs foram utilizados para evitar:

* Exposição de entidades diretamente
* Vazamento de dados sensíveis
* Acoplamento entre API e banco

### Tipos utilizados:

* **RequestDTO** → Entrada de dados
* **ResponseDTO** → Saída de dados

---

# ⚠️ Tratamento de Erros

A API possui tratamento global com `@ControllerAdvice`.

## Tipos de erro:

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

# 📌 Endpoints Principais

## 👤 Clientes

| Método | Endpoint                     | Descrição         |
| ------ | ---------------------------- | ----------------- |
| GET    | /api/v1/clientes             | Listar todos      |
| GET    | /api/v1/clientes/{id}        | Buscar por ID     |
| GET    | /api/v1/clientes/busca?nome= | Buscar por nome   |
| POST   | /api/v1/clientes             | Criar cliente     |
| PUT    | /api/v1/clientes/{id}        | Atualizar cliente |
| DELETE | /api/v1/clientes/{id}        | Remover cliente   |

---

## ✂️ Barbeiros

| Método | Endpoint               |
| ------ | ---------------------- |
| GET    | /api/v1/barbeiros      |
| POST   | /api/v1/barbeiros      |
| PUT    | /api/v1/barbeiros/{id} |
| DELETE | /api/v1/barbeiros/{id} |

---

## 💇 Serviços

| Método | Endpoint              |
| ------ | --------------------- |
| GET    | /api/v1/servicos      |
| POST   | /api/v1/servicos      |
| PUT    | /api/v1/servicos/{id} |
| DELETE | /api/v1/servicos/{id} |

---

## 📅 Agendamentos

| Método | Endpoint                  |
| ------ | ------------------------- |
| GET    | /api/v1/agendamentos      |
| POST   | /api/v1/agendamentos      |
| DELETE | /api/v1/agendamentos/{id} |

---

# 📥 Exemplos de Requisição

## 🔹 Criar Cliente

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

## 🔹 Criar Barbeiro

```bash
curl -X POST http://localhost:8080/api/v1/barbeiros \
-H "Content-Type: application/json" \
-d '{
  "nome": "Carlos"
}'
```

---

## 🔹 Criar Serviço

```bash
curl -X POST http://localhost:8080/api/v1/servicos \
-H "Content-Type: application/json" \
-d '{
  "nome": "Corte",
  "preco": 30.00
}'
```

---

## 🔹 Criar Agendamento

```bash
curl -X POST http://localhost:8080/api/v1/agendamentos \
-H "Content-Type: application/json" \
-d '{
  "cliente": { "id": 1 },
  "barbeiro": { "id": 1 },
  "servicos": [{ "id": 1 }]
}'
```

---

# 🧪 Testes

O projeto segue a pirâmide de testes:

* **Repository** → @DataJpaTest
* **Service** → Mockito
* **Controller** → @WebMvcTest

Para rodar:

```bash
./mvnw test
```

---

# 📊 Cobertura de Código

Relatório gerado com JaCoCo:

```
target/site/jacoco/index.html
```

Meta: **≥ 70%**

---

# 📘 Documentação Swagger

Acesse:

```
http://localhost:8080/swagger-ui.html
```

Permite testar todos os endpoints diretamente pelo navegador.

---

# ⚙️ Como Rodar o Projeto

### 1. Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

### 2. Configurar banco de dados no application.properties

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

# 👨‍💻 Autores

* Filipe Bento
* Hytalo Leão

---

# 🎯 Status do Projeto

✔ CRUD completo
✔ Validações
✔ Tratamento de erros
✔ DTOs implementados
✔ API documentada
✔ Pronto para testes e apresentação

---

# 🚀 Próximas Melhorias

* Regras de negócio para agendamento (horários, disponibilidade)
* Autenticação (Spring Security + JWT)
* Paginação
* Logs estruturados
* Deploy em nuvem

---

# 📌 Conclusão

O projeto implementa uma API REST completa seguindo boas práticas de arquitetura, separação de responsabilidades e validação de dados, estando preparado para evolução e uso em cenários reais.
