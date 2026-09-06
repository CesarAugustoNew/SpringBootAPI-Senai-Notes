package com.senainotes.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
  Campos em inglês (name/email/password) para bater com o que o
  front-end envia em POST /users (ver new-user-screen.ts).
*/
public record UsuarioRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        @Pattern(regexp = ".*[A-Z].*", message = "A senha deve ter ao menos uma letra maiúscula")
        String password
) {
}
