package com.senainotes.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
  Campos em inglês (email/password) para bater exatamente com o que
  o front-end Angular envia (ver login-screen.ts). O exemplo original
  usava "email"/"senha", o que fazia "senha" nunca ser preenchida.
*/
public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
