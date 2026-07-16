package com.senainotes.api.dto.response;

/*
  Nomes dos campos batendo 1:1 com o que o front-end espera em
  login-screen.ts: response.accessToken e response.user.id.
  O exemplo original devolvia { token, usuario }, por isso o
  front-end nunca conseguia ler o token nem o id do usuário.
*/
public record LoginResponse(
        String accessToken,
        UsuarioResponse user
) {
}
