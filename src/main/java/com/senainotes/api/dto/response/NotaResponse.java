package com.senainotes.api.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

/*
  Campos batendo 1:1 com a interface NoteItem do front-end
  (ver notes-list.ts): id, userId, title, description, tags, image,
  date, archived.
*/
public record NotaResponse(
        Integer id,
        Integer userId,
        String title,
        String description,
        List<String> tags,
        String image,
        OffsetDateTime date,
        Boolean archived
) {
}
