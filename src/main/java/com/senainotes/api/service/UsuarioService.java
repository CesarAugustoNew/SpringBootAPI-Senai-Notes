package com.senainotes.api.service;

import com.senainotes.api.dto.request.UsuarioRequest;
import com.senainotes.api.dto.response.UsuarioResponse;
import com.senainotes.api.model.Usuario;
import com.senainotes.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponse cadastrar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Este e-mail já está cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.name());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.password()));
        usuario.setDataCriacao(OffsetDateTime.now());

        return toResponse(usuarioRepository.save(usuario));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UsuarioResponse buscarResponsePorEmail(String email) {
        return toResponse(buscarPorEmail(email));
    }

    public UsuarioResponse buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
