package com.senainotes.api.controller;

import com.senainotes.api.dto.request.UsuarioRequest;
import com.senainotes.api.dto.response.UsuarioResponse;
import com.senainotes.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
  Rota ajustada para POST /users (raiz), batendo com
  new-user-screen.ts. O exemplo original usava POST /api/usuarios/cadastrar.
*/
@Tag(name = "Usuários", description = "Cadastro de usuário")
@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cria uma nova conta de usuário")
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest request) {
        return new ResponseEntity<>(usuarioService.cadastrar(request), HttpStatus.CREATED);
    }
}
