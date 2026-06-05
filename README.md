# 💈 API de Agendamento de Barbearia

Projeto desenvolvido para a disciplina de **Programação Cliente-Servidor com Spring Boot (UNIBRA 2026.1)**.

A aplicação consiste em uma **API REST completa** para gerenciamento de uma barbearia, permitindo o cadastro de **clientes, barbeiros, serviços e agendamentos**.

---

# 🚀 Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven
* H2 Database (desenvolvimento)
* MySQL (produção)
* Swagger (OpenAPI)
* Lombok
* Jakarta Validation
* JaCoCo (cobertura de testes)

---

# 📁 Estrutura do Projeto

```
src/main/java/com/filipe_bento/agendamento_barbearia/

├── controller/        → Camada REST (entrada/saída HTTP)
├── service/           → Regras de negócio
├── repository/        → Acesso ao banco de dados
├── entity/            → Entidades JPA
├── dto/               → RequestDTO e ResponseDTO
├── exception/         → Tratamento global de erros
├── config/            → Configurações da aplicação
```

---

# 🧠 Arquitetura

A aplicação segue o padrão:

```
Controller → DTO → Service → Repository → Banco de Dados
```

### 📌 Responsabilidades

* **Controller:** recebe requisições HTTP
* **DTO:** controla entrada e saída de dados
* **Service:** regras de negócio
* **Repository:** acesso ao banco

---

# 📅 Fluxo de Agendamento

1. Recebe um `AgendamentoRequestDTO`
2. Busca Cliente, Barbeiro e Serviços
3. Valida existência
4. Monta o objeto
5. Salva no banco

---

# 🧠 Regras de Negócio

## 👤 Validação de Existência

* Cliente, Barbeiro e Serviço devem existir

## 📅 Agendamento válido

* Não permite dados inexistentes

## 🔗 Relacionamentos

* 1 Cliente
* 1 Barbeiro
* 1 ou mais Serviços

## 📧 Email único

* Não permite duplicidade de cliente

## 🧾 Validação de dados

* Nome obrigatório
* Email válido

## 🔄 Atualização segura

* Só atualiza se existir

## ❌ Exclusão segura

* Só remove se existir

---

# ⚠️ Tratamento de Erros

Utiliza `@ControllerAdvice`

### 🔴 404

```json
{
  "status": 404,
  "message": "Recurso não encontrado"
}
```

### 🟡 400

```json
{
  "status": 400,
  "message": "Erro de validação"
}
```

---

# 📌 Endpoints

## 👤 Clientes

* GET /api/v1/clientes
* GET /api/v1/clientes/{id}
* GET /api/v1/clientes/busca
* POST /api/v1/clientes
* PUT /api/v1/clientes/{id}
* DELETE /api/v1/clientes/{id}

## ✂️ Barbeiros

* GET /api/v1/barbeiros
* POST /api/v1/barbeiros
* PUT /api/v1/barbeiros/{id}
* DELETE /api/v1/barbeiros/{id}

## 💇 Serviços

* GET /api/v1/servicos
* POST /api/v1/servicos
* PUT /api/v1/servicos/{id}
* DELETE /api/v1/servicos/{id}

## 📅 Agendamentos

* GET /api/v1/agendamentos
* POST /api/v1/agendamentos
* PUT /api/v1/agendamentos/{id}
* DELETE /api/v1/agendamentos/{id}

---

# 📥 Exemplos

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

# 🧪 Testes

### Estrutura

* Repository → `@DataJpaTest`
* Service → Mockito
* Controller → `@WebMvcTest`

### Executar

```bash
./mvnw test
```

---

# 📊 Cobertura de Código (JaCoCo)

Relatório:

```
target/site/jacoco/index.html
```

### Resultados

* Cobertura: **83%**
* Branches: **86%**

### Análise

* Service: ~94%
* Controller: 100%
* DTO: 100%
* Exception: ~34%

---

# 📘 Swagger

Acesse:

```
http://localhost:8080/swagger-ui.html
```

Permite testar todos os endpoints.

---

# ⚙️ Como Rodar

```bash
git clone <URL>
cd projeto
./mvnw spring-boot:run
```

---

# 📌 Diferenciais

* Arquitetura em camadas
* Uso de DTOs
* Tratamento global de erros
* Testes automatizados
* JaCoCo
* Swagger

---

# 👨‍💻 Autores

* Filipe Bento
* Hytalo Leão

---

# 🎯 Status

✔ CRUD completo
✔ DTOs
✔ Testes
✔ Swagger
✔ Pronto para apresentação

---

# 🚀 Melhorias Futuras

* Validação de horários
* Disponibilidade de barbeiro
* Autenticação (JWT)
* Paginação
* Deploy em nuvem

---

# 📌 Conclusão

A API foi desenvolvida seguindo boas práticas de arquitetura, garantindo organização, segurança e escalabilidade, estando preparada para evolução e uso em cenários reais.
