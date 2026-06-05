💈 API de Agendamento de Barbearia

Projeto desenvolvido para a disciplina de Programação Cliente-Servidor com Spring Boot (UNIBRA 2026.1).

A aplicação consiste em uma API REST completa para gerenciamento de uma barbearia, permitindo o cadastro de clientes, barbeiros, serviços e agendamentos.

🚀 Tecnologias Utilizadas
Java 17+
Spring Boot
Spring Data JPA
Hibernate
Maven
H2 Database (desenvolvimento)
MySQL (produção)
Swagger (OpenAPI)
Lombok
Jakarta Validation
JaCoCo (cobertura de testes)
📁 Estrutura do Projeto
src/main/java/com/filipe_bento/agendamento_barbearia/

├── controller/        → Camada REST (entrada/saída HTTP)
├── service/           → Regras de negócio
├── repository/        → Acesso ao banco de dados
├── entity/            → Entidades JPA
├── dto/               → RequestDTO e ResponseDTO
├── exception/         → Tratamento global de erros
├── config/            → Configurações da aplicação
🧠 Arquitetura

A aplicação segue o padrão MVC + Service Layer:

Controller → DTO → Service → Repository → Banco de Dados
📌 Responsabilidades:
Controller: recebe requisições HTTP
DTO: controla entrada e saída de dados
Service: contém regras de negócio
Repository: comunicação com banco
📅 Fluxo de Agendamento

Ao criar um agendamento:

A API recebe um AgendamentoRequestDTO
O Service busca:
Cliente
Barbeiro
Serviços
Valida se os dados existem
Monta o objeto Agendamento
Persiste no banco
🔗 Relacionamentos:
Cliente → ManyToOne
Barbeiro → ManyToOne
Serviços → ManyToMany
🔐 Uso de DTOs

Os DTOs foram utilizados para evitar:

Exposição direta das entidades
Vazamento de dados sensíveis
Acoplamento entre API e banco
Tipos:
RequestDTO: entrada de dados
ResponseDTO: saída de dados
⚠️ Tratamento de Erros

A API possui tratamento global com @ControllerAdvice.

🔴 404 - Recurso não encontrado
{
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "Cliente não encontrado com o ID: 1",
  "path": "/api/v1/clientes/1"
}
🟡 400 - Erro de validação
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
📌 Endpoints Principais
👤 Clientes
Método	Endpoint	Descrição
GET	/api/v1/clientes	Listar todos
GET	/api/v1/clientes/{id}	Buscar por ID
GET	/api/v1/clientes/busca?nome=	Buscar por nome
POST	/api/v1/clientes	Criar cliente
PUT	/api/v1/clientes/{id}	Atualizar
DELETE	/api/v1/clientes/{id}	Remover
✂️ Barbeiros
Método	Endpoint
GET	/api/v1/barbeiros
GET	/api/v1/barbeiros/{id}
GET	/api/v1/barbeiros/busca
POST	/api/v1/barbeiros
PUT	/api/v1/barbeiros/{id}
DELETE	/api/v1/barbeiros/{id}
💇 Serviços
Método	Endpoint
GET	/api/v1/servicos
GET	/api/v1/servicos/{id}
POST	/api/v1/servicos
PUT	/api/v1/servicos/{id}
DELETE	/api/v1/servicos/{id}
📅 Agendamentos
Método	Endpoint
GET	/api/v1/agendamentos
GET	/api/v1/agendamentos/{id}
POST	/api/v1/agendamentos
PUT	/api/v1/agendamentos/{id}
DELETE	/api/v1/agendamentos/{id}
📥 Exemplos de Requisição
🔹 Criar Cliente
curl -X POST http://localhost:8080/api/v1/clientes \
-H "Content-Type: application/json" \
-d '{
  "nome": "Filipe",
  "telefone": "99999-9999",
  "email": "filipe@email.com"
}'
🔹 Criar Agendamento
curl -X POST http://localhost:8080/api/v1/agendamentos \
-H "Content-Type: application/json" \
-d '{
  "cliente": { "id": 1 },
  "barbeiro": { "id": 1 },
  "servicos": [{ "id": 1 }]
}'
🗄️ Banco de Dados
Desenvolvimento:
H2 Database (em memória)

✔ Não precisa instalação
✔ Ideal para testes
✔ Reinicia a cada execução

Produção:
MySQL
🧪 Testes

O projeto segue a pirâmide de testes:

🔹 Repository (@DataJpaTest)

Testa persistência no banco H2

Exemplos:

Salvar entidade
Buscar por ID
Consultas customizadas
🔹 Service (Mockito)

Testa regras de negócio isoladas

Exemplos:

Validação de entidade inexistente
Atualização de dados
🔹 Controller (@WebMvcTest)

Testa endpoints HTTP

Exemplos:

POST retorna 201
GET retorna lista
Erro 404
▶️ Executar testes
./mvnw test
📊 Cobertura de Código (JaCoCo)

Relatório gerado em:

target/site/jacoco/index.html
📈 Resultados:
Cobertura de Instruções: 83%
Cobertura de Branches: 86%
📌 Análise:

✔ Service: ~94%
✔ Controller: 100%
✔ DTO/Config: 100%
⚠️ Exception: ~34%

🎯 Conclusão

A cobertura supera a meta de 70%, garantindo boa validação das funcionalidades principais.

📘 Documentação Swagger

Acesse:

http://localhost:8080/swagger-ui.html
Permite:

✔ Visualizar endpoints
✔ Testar requisições
✔ Ver exemplos

⚙️ Como Rodar o Projeto
1. Clonar
git clone <URL_DO_REPOSITORIO>
2. Rodar
./mvnw spring-boot:run
📌 Diferenciais do Projeto

✔ Uso de DTOs
✔ Tratamento global de exceções
✔ Swagger
✔ Testes automatizados
✔ JaCoCo
✔ Arquitetura em camadas

👨‍💻 Autores
Filipe Bento
Hytalo Leão
🎯 Status

✔ CRUD completo
✔ Validações
✔ DTOs
✔ Testes
✔ Swagger
✔ Pronto para apresentação

🚀 Próximas Melhorias
Regras de horário
Autenticação (JWT)
Paginação
Deploy em nuvem
📌 Conclusão

A API foi desenvolvida seguindo boas práticas de arquitetura, separação de responsabilidades e validação de dados, estando preparada para evolução e uso em cenários reais.
