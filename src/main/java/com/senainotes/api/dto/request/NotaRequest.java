package com.senainotes.api.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

/*
  Usado tanto para criar (POST) quanto para atualizar (PUT) uma nota.
  Recebe JSON puro — o exemplo original exigia multipart/form-data
  com upload de arquivo, mas o front-end manda a nota inteira (título,
  descrição, tags, imagem em base64) como um único corpo JSON (ver
  note.ts e notes-list.ts).
*/
public record NotaRequest(
        @NotBlank String title,
        String description,
        List<String> tags,
        String image,
        Boolean archived
) {
}
