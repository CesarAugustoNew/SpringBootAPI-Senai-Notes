package com.senainotes.api.controller;

import com.senainotes.api.dto.request.ArquivarRequest;
import com.senainotes.api.dto.request.NotaRequest;
import com.senainotes.api.dto.response.NotaResponse;
import com.senainotes.api.model.Usuario;
import com.senainotes.api.service.NotaService;
import com.senainotes.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  Rota ajustada para /senainotes/notes, batendo com notes-list.ts,
  note.ts e note-options.ts. O exemplo original usava /api/notas,
  exigia multipart/form-data com upload de arquivo, e o GET listava
  as notas de TODOS os usuários (sem filtrar pelo usuário logado).
*/
@Tag(name = "Notas", description = "CRUD de notas do usuário autenticado")
@RestController
@RequestMapping("/senainotes/notes")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar minhas notas", description = "Lista todas as notas do usuário autenticado")
    public ResponseEntity<List<NotaResponse>> listar(Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        return ResponseEntity.ok(notaService.listarDoUsuario(usuario));
    }

    @PostMapping
    @Operation(summary = "Criar nota", description = "Cria uma nova nota para o usuário autenticado")
    public ResponseEntity<NotaResponse> criar(@Valid @RequestBody NotaRequest request, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        return new ResponseEntity<>(notaService.criar(request, usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar nota", description = "Atualiza título, descrição, tags e/ou imagem de uma nota")
    public ResponseEntity<NotaResponse> atualizar(
            @PathVariable Integer id, @Valid @RequestBody NotaRequest request, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        return ResponseEntity.ok(notaService.atualizar(id, request, usuario));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Arquivar/desarquivar nota", description = "Alterna o campo archived de uma nota")
    public ResponseEntity<NotaResponse> arquivar(
            @PathVariable Integer id, @Valid @RequestBody ArquivarRequest request, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        return ResponseEntity.ok(notaService.arquivar(id, request.archived(), usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover nota")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        notaService.deletar(id, usuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
