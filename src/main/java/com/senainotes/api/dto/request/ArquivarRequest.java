package com.senainotes.api.dto.request;

import jakarta.validation.constraints.NotNull;

/*
  Corpo do PATCH /senainotes/notes/{id} usado para arquivar/desarquivar
  (ver note-options.ts). Esse endpoint não existia no back-end de
  exemplo.
*/
public record ArquivarRequest(
        @NotNull Boolean archived
) {
}
