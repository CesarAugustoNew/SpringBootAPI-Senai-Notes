<div align="center">

# Senai Notes API

Back-end de um sistema de anotações com login e permissões próprias, construído em **Java** com **Spring Boot**.

[![Backend](https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?style=flat-square&logo=spring&logoColor=white)](#)
[![Java](https://img.shields.io/badge/Java-21-007396?style=flat-square&logo=openjdk&logoColor=white)](#)
[![Database](https://img.shields.io/badge/Database-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)](#)
[![Deploy](https://img.shields.io/badge/Deploy-Render-46E3B7?style=flat-square&logo=render&logoColor=white)](#)
[![Docker](https://img.shields.io/badge/Container-Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](#)

[**🔗 API publicada**](https://springbootapi-senai-notes.onrender.com) · [**📘 Documentação (Swagger)**](https://springbootapi-senai-notes.onrender.com/swagger-ui.html) · [**🖥️ Repositório do front-end**](#)

</div>

---

## Sobre este projeto

Esta é a API REST que guarda e organiza os dados do Senai Notes: usuários, notas, etiquetas e todo o controle de quem pode ver o quê. Ela não tem tela nenhuma — só recebe pedidos (de um front-end, ou de qualquer outro programa) e responde em formato de dados (JSON).

O front-end que consome esta API é um projeto separado, feito em Angular ([link do repositório](#)).

## O que a API faz

- Cadastro de usuário e login com senha
- Emissão de um token de acesso (JWT) após o login, usado para provar quem é o usuário nas próximas ações
- Criar, listar, editar e excluir notas (só as notas do próprio usuário logado)
- Arquivar/desarquivar notas
- Listar as etiquetas (tags) já usadas pelo usuário

## Tecnologias usadas

| Item | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot |
| Acesso ao banco | Spring Data JPA (Hibernate) |
| Autenticação | Spring Security + JWT |
| Banco de dados | PostgreSQL |
| Empacotamento | Docker |
| Publicação | Render |

## Como a API é organizada

Ela segue o modelo mais comum para esse tipo de projeto, dividido em camadas com responsabilidades separadas:

- **Controller** — recebe a requisição HTTP (ex.: "criar uma nota") e devolve a resposta.
- **Service** — contém as regras de negócio (ex.: "só o dono da nota pode editá-la").
- **Repository** — é quem efetivamente conversa com o banco de dados.

Essa separação existe para que cada parte tenha uma responsabilidade clara: o Controller não sabe como o banco funciona, e o banco não sabe nada sobre HTTP — facilita achar e corrigir problemas, e também testar cada parte separadamente.

## Segurança

O login funciona com **JWT** (JSON Web Token): ao entrar com e-mail e senha, o usuário recebe um token que deve ser enviado nas próximas requisições, provando quem ele é sem precisar mandar a senha de novo. Isso também garante que uma pessoa nunca acesse ou altere notas de outra conta — cada consulta já filtra automaticamente pelo usuário dono do token.

## Deploy

Publicada no **Render**, com banco de dados **PostgreSQL** também hospedado lá. A aplicação é empacotada com **Docker**, o que permite publicá-la de forma consistente em praticamente qualquer provedor de nuvem.
