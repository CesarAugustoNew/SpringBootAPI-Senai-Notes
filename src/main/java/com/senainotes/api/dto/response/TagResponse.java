package com.senainotes.api.dto.response;

/*
  O front-end só lê "tag.name" (ver left-panel.html/ts) — o "id" é só
  para servir de chave no *ngFor, não precisa ser um ID relacional de
  verdade. Por isso, usamos o próprio nome da tag como id (ele já é
  único por usuário).
*/
public record TagResponse(
        String id,
        String name
) {
}
