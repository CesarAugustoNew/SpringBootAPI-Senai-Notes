# 📝 Senai Notes — API

API RESTful em **Java + Spring Boot** para o aplicativo de notas Senai Notes (login, cadastro, notas com tags e imagem, arquivamento).

## 🛠 Tecnologias

- Java 21, Spring Boot 3.5
- Spring Web, Spring Data JPA (Hibernate)
- Spring Security + OAuth2 Resource Server (JWT via Nimbus, HMAC-SHA256)
- PostgreSQL
- Maven, Lombok
- Swagger / OpenAPI (springdoc)

---

## ▶️ Como rodar

### Pré-requisitos
- JDK 21+
- PostgreSQL rodando localmente na porta `5432`

### 1. Criar o banco
```sql
CREATE DATABASE senainotes;
```
As tabelas são criadas automaticamente (`ddl-auto: update`), dentro do schema `notes`.

### 2. Configurar credenciais (opcional)
Por padrão usa `postgres`/`postgres` em `localhost:5432/senainotes`. Para mudar, crie um arquivo `.env` na raiz do projeto:
```
DB_URL=jdbc:postgresql://localhost:5432/senainotes
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
```

### 3. Subir a aplicação
```bash
./mvnw spring-boot:run
```
A API sobe em `http://localhost:8080`.

---

## 📖 Swagger

```
http://localhost:8080/swagger-ui.html
```

---

## 🔌 Endpoints

| Recurso | Método | Rota | Acesso |
|---|---|---|---|
| Login | POST | `/login` | Público |
| Cadastro | POST | `/users` | Público |
| Notas | GET | `/senainotes/notes` | Autenticado (só as suas) |
| Notas | POST | `/senainotes/notes` | Autenticado |
| Notas | PUT | `/senainotes/notes/{id}` | Autenticado (só as suas) |
| Notas | PATCH | `/senainotes/notes/{id}` (arquivar/desarquivar) | Autenticado (só as suas) |
| Notas | DELETE | `/senainotes/notes/{id}` | Autenticado (só as suas) |
| Tags | GET | `/senainotes/tags` | Autenticado (só as suas) |

---

## 📂 Estrutura

```
src/main/java/com/senainotes/api
├── controller
├── service
├── repository
├── model
├── dto
│   ├── request
│   └── response
├── config       # segurança JWT, swagger
└── exception    # tratamento global de erros
```

---
