# 📝 Senai Notes — API

API RESTful em **Java + Spring Boot** para o aplicativo de notas Senai Notes (login, cadastro, notas com tags e imagem, arquivamento). Construída a partir de um back-end de exemplo de outro repositório, **corrigida e adaptada** para bater exatamente com o contrato que o front-end Angular (`Projeto-Senai-Notes-Angular`) espera.

---

## ⚠️ O front-end também precisa ser ajustado

O front-end Angular, do jeito que está, chama uma URL **errada** (herdada por engano de outro projeto do curso):
```
https://senai-gpt-api.azurewebsites.net
```
em `login-screen.ts`, `new-user-screen.ts`, `notes-list.ts`, `note.ts`, `note-options.ts` e `left-panel.ts`.

Para o app funcionar de ponta a ponta, essas URLs precisam apontar para **esta API** (ex.: `http://localhost:8080` em desenvolvimento, com os caminhos `/login`, `/users`, `/senainotes/notes`, `/senainotes/tags` preservados — foi exatamente para bater com esses caminhos que esta API foi ajustada). Posso fazer essa troca no front-end também, se você quiser — é só pedir.

---

## 🛠 O que foi corrigido em relação ao back-end de exemplo

| Problema no exemplo | Correção |
|---|---|
| Rotas `/api/login`, `/api/usuarios/cadastrar`, `/api/notas`, `/api/tag` | Trocadas para `/login`, `/users`, `/senainotes/notes`, `/senainotes/tags` — as que o front-end realmente chama |
| `LoginRequest` esperava `{ email, senha }` | Front-end manda `{ email, password }` — campo `senha` nunca era preenchido |
| `LoginResponse` devolvia `{ token, usuario }` | Front-end lê `response.accessToken` e `response.user.id` — nomes agora batem |
| Cadastro de nota exigia `multipart/form-data` com upload de arquivo | Front-end manda a nota inteira como **JSON**, com a imagem em base64 — endpoint reescrito para aceitar JSON puro |
| `GET /api/notas` devolvia as notas de **todos os usuários do sistema** | Agora `GET /senainotes/notes` só devolve as notas do usuário autenticado (extraído do token) |
| Não existia endpoint de arquivar/desarquivar | Adicionado `PATCH /senainotes/notes/{id}` com `{ archived: true/false }` |
| Tag era uma entidade própria (`@ManyToOne` para uma única nota); reaproveitar o nome de uma tag existente **reatribuía** essa tag para a nota nova, removendo-a silenciosamente da nota antiga | Tags agora são só uma lista de strings por nota (`@ElementCollection`) — sem essa entidade compartilhada, sem esse bug |
| Dependia de AWS S3 (upload de imagem) e SMTP (recuperação de senha), exigindo credenciais externas | Removido — imagem é salva como string (base64) direto no banco; recuperação de senha não fazia parte do que o front-end usa hoje |

---

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

## ⚠️ Pontos de atenção

- Tentar editar/arquivar/apagar uma nota que não é sua devolve `403 Forbidden`.
- Não há endpoint de recuperação/troca de senha nesta versão — o exemplo original tinha um fluxo por e-mail (SMTP) que exigia credenciais externas e não é usado pelo front-end atual.
