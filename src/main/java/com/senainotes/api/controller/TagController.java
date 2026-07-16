package com.senainotes.api.controller;

import com.senainotes.api.dto.response.TagResponse;
import com.senainotes.api.model.Usuario;
import com.senainotes.api.service.NotaService;
import com.senainotes.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
  Rota ajustada para /senainotes/tags, batendo com left-panel.ts.
  O exemplo original usava /api/tag e recebia o e-mail do usuário
  como query param — aqui o usuário vem sempre do token JWT.
*/
@Tag(name = "Tags", description = "Tags usadas nas notas do usuário autenticado")
@RestController
@RequestMapping("/senainotes/tags")
public class TagController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar minhas tags", description = "Lista os nomes de tag distintos usados nas notas do usuário autenticado")
    public ResponseEntity<List<TagResponse>> listar(Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        return ResponseEntity.ok(notaService.listarTagsDoUsuario(usuario));
    }
}
