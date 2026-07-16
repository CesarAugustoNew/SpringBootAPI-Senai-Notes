package com.senainotes.api.service;

import com.senainotes.api.dto.request.NotaRequest;
import com.senainotes.api.dto.response.NotaResponse;
import com.senainotes.api.dto.response.TagResponse;
import com.senainotes.api.model.Nota;
import com.senainotes.api.model.Usuario;
import com.senainotes.api.repository.NotaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    /*
      Lista SOMENTE as notas do usuário autenticado. O back-end de
      exemplo tinha um GET /api/notas que devolvia as notas de TODOS
      os usuários do sistema (e endpoints separados por e-mail/id para
      filtrar) — aqui a filtragem pelo usuário logado é automática e
      única, batendo com o único endpoint que o front-end usa
      (GET /senainotes/notes).
    */
    public List<NotaResponse> listarDoUsuario(Usuario usuario) {
        return notaRepository.findByUsuarioIdOrderByUltimaEdicaoDesc(usuario.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public NotaResponse criar(NotaRequest request, Usuario usuario) {
        OffsetDateTime agora = OffsetDateTime.now();

        Nota nota = new Nota();
        nota.setUsuario(usuario);
        nota.setTitulo(request.title());
        nota.setDescricao(request.description());
        nota.setImagem(request.image());
        nota.setTags(limparTags(request.tags()));
        nota.setArquivado(request.archived() != null && request.archived());
        nota.setDataCriacao(agora);
        nota.setUltimaEdicao(agora);

        return toResponse(notaRepository.save(nota));
    }

    public NotaResponse atualizar(Integer id, NotaRequest request, Usuario usuario) {
        Nota nota = buscarDoUsuario(id, usuario);

        nota.setTitulo(request.title());
        nota.setDescricao(request.description());
        nota.setTags(limparTags(request.tags()));
        nota.setUltimaEdicao(OffsetDateTime.now());

        // A imagem só é trocada se vier preenchida — assim, salvar a
        // nota sem mexer na foto não apaga a imagem já existente.
        if (request.image() != null && !request.image().isBlank()) {
            nota.setImagem(request.image());
        }

        if (request.archived() != null) {
            nota.setArquivado(request.archived());
        }

        return toResponse(notaRepository.save(nota));
    }

    /*
      PATCH /senainotes/notes/{id} — arquivar/desarquivar (ver
      note-options.ts). Esse endpoint simplesmente não existia antes.
    */
    public NotaResponse arquivar(Integer id, boolean arquivado, Usuario usuario) {
        Nota nota = buscarDoUsuario(id, usuario);
        nota.setArquivado(arquivado);
        nota.setUltimaEdicao(OffsetDateTime.now());
        return toResponse(notaRepository.save(nota));
    }

    public void deletar(Integer id, Usuario usuario) {
        Nota nota = buscarDoUsuario(id, usuario);
        notaRepository.delete(nota);
    }

    /*
      GET /senainotes/tags — como não existe mais uma entidade Tag
      própria (ver Nota.java), a lista de tags é simplesmente o
      conjunto de nomes distintos usados nas notas do usuário.
    */
    public List<TagResponse> listarTagsDoUsuario(Usuario usuario) {
        Set<String> nomes = new LinkedHashSet<>();

        for (Nota nota : notaRepository.findByUsuarioIdOrderByUltimaEdicaoDesc(usuario.getId())) {
            nomes.addAll(limparTags(nota.getTags()));
        }

        return nomes.stream().map(nome -> new TagResponse(nome, nome)).toList();
    }

    private Nota buscarDoUsuario(Integer id, Usuario usuario) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota não encontrada"));

        if (!nota.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Essa nota não pertence a você");
        }

        return nota;
    }

    private List<String> limparTags(List<String> tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        return tags.stream()
                .filter(tag -> tag != null && !tag.isBlank())
                .map(String::trim)
                .distinct()
                .toList();
    }

    private NotaResponse toResponse(Nota nota) {
        return new NotaResponse(
                nota.getId(),
                nota.getUsuario().getId(),
                nota.getTitulo(),
                nota.getDescricao(),
                nota.getTags(),
                nota.getImagem(),
                nota.getUltimaEdicao(),
                nota.getArquivado()
        );
    }
}
