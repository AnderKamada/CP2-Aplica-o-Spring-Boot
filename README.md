CP2 – Aplicação Spring Boot (Authors & Books)

Aplicação Spring Boot 3 com CRUD de Authors e Books, relacionamento Author (1) — (N) Book, camadas Controller / Service (interface + impl) / Repository, DTOs como record, Mapper, exceção customizada com @RestControllerAdvice, banco H2 em memória e testes (JUnit 5 + MockMvc) com relatório JaCoCo (≥ 50%).

🔧 Stack

Java 17

Spring Boot (Web, Data JPA, Validation)

H2 (in-memory)

Maven

JUnit 5, Spring Test / MockMvc

JaCoCo

🚀 Como executar

Pré-requisitos: JDK 17 e Maven instalados.

# build + testes + cobertura
mvn clean verify

# subir a aplicação
mvn spring-boot:run


API: http://localhost:8080

Console H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:checkpoint2db

User: sa • Password: (vazio)

Cobertura JaCoCo: após mvn clean verify, abra target/site/jacoco/index.html.

⚙️ Configuração (H2 & JPA)

src/main/resources/application.properties (exemplo):

spring.datasource.url=jdbc:h2:mem:checkpoint2db;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console


Testes (src/test/resources/application-test.properties, opcional):

spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always

🧱 Domínio & DTOs

Author

id: Long, name: String, email: String (único)

books: Set<Book>

Book

id: Long, title: String, isbn: String (único)

author: Author (FK)

DTOs (record)

public record AuthorRecord(Long id, String name, String email) {}
public record BookRecord(Long id, String title, String isbn, Long authorId) {}


Validações: jakarta.validation (ex.: @NotBlank, @NotNull) nos DTOs e @Valid nos Controllers.

🧭 Endpoints
Authors
Método	Caminho	Descrição	Status
POST	/authors	Cria autor	201
GET	/authors	Lista autores	200
GET	/authors/{id}	Detalha autor	200
PUT	/authors/{id}	Atualiza autor	200
DELETE	/authors/{id}	Remove autor	204
GET	/authors/{id}/books	Lista livros do autor	200
Books
Método	Caminho	Descrição	Status
POST	/books	Cria livro	201
GET	/books	Lista livros	200
GET	/books/{id}	Detalha livro	200
PUT	/books/{id}	Atualiza livro	200
DELETE	/books/{id}	Remove livro	204
📥 Exemplos de requisição
cURL (cmd/Terminal)
# criar autor
curl -X POST http://localhost:8080/authors \
  -H "Content-Type: application/json" \
  -d '{"name":"Ada Lovelace","email":"ada@lovelace.dev"}'

# listar autores
curl http://localhost:8080/authors

# criar livro (ajuste o authorId)
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Notes","isbn":"ISBN-001","authorId":1}'

# listar livros
curl http://localhost:8080/books

# listar livros de um autor
curl http://localhost:8080/authors/1/books

PowerShell (use Invoke-RestMethod ou force o curl.exe)
# criar autor
Invoke-RestMethod -Uri 'http://localhost:8080/authors' -Method Post `
  -ContentType 'application/json' `
  -Body '{"name":"Ada Lovelace","email":"ada@lovelace.dev"}'

IntelliJ HTTP Client

Crie requests.http na raiz e cole:

### criar autor
POST http://localhost:8080/authors
Content-Type: application/json

{
  "name": "Ada Lovelace",
  "email": "ada@lovelace.dev"
}

### listar autores
GET http://localhost:8080/authors

### criar livro
POST http://localhost:8080/books
Content-Type: application/json

{
  "title": "Notes",
  "isbn": "ISBN-001",
  "authorId": 1
}

### livros do autor
GET http://localhost:8080/authors/1/books

🧪 Testes & Cobertura

Unitários e de integração (MockMvc).

Gerar e abrir cobertura:

mvn clean verify
# abrir target/site/jacoco/index.html


Meta: ≥ 50% de linhas cobertas.

🧰 Tratamento de erros

ResourceNotFoundException → 404 via @RestControllerAdvice.
Exemplo de resposta:

{
  "timestamp": "2025-10-03T14:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Author not found with id: 999",
  "path": "/authors/999"
}


Validações de entrada retornam 400 com detalhes dos campos (quando configurado no handler global).

🗂️ Estrutura de pastas (camadas)
app/                         # @SpringBootApplication (+ @EntityScan / @EnableJpaRepositories)
controller/                  # REST Controllers
service/                     # Interfaces de serviço
service/impl/                # Implementações
repository/                  # JPA Repositories
entity/                      # Entidades JPA
dto/                         # DTOs como record
mapper/                      # Interface + implementação
exception/                   # Exception + GlobalExceptionHandler

✔️ Checklist do trabalho

 Duas entidades relacionadas (Author ↔ Book)

 CRUD completo

 GET /authors/{id}/books

 Camadas com interfaces (service/mapper/repository)

 DTOs como record

 Mapper (entity ↔ DTO)

 Exceção customizada + Controller Advice

 H2 ativo + console

 Testes com JaCoCo ≥ 50%

 Pelo menos 1 teste de integração
