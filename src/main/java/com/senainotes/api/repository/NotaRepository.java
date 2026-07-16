package com.senainotes.api.repository;

import com.senainotes.api.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {
    /*
      Todas as notas listadas/atualizadas pela API são sempre filtradas
      pelo usuário autenticado (ver NotaService) — nunca retornamos
      notas de outros usuários, ao contrário do exemplo original, cujo
      GET /api/notas devolvia TODAS as notas do sistema, de qualquer
      usuário.
    */
    List<Nota> findByUsuarioIdOrderByUltimaEdicaoDesc(Integer usuarioId);
}
