package com.senainotes.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/*
  IMPORTANTE — simplificação em relação ao exemplo original:

  O projeto de exemplo modelava tag como uma entidade própria
  (Tag), onde cada linha de tag pertencia a exatamente UMA nota
  (@ManyToOne Nota). O service "reaproveitava" uma tag existente
  pelo nome quando uma nova nota usava o mesmo nome de tag — mas
  como o vínculo é de mão única (uma tag só aponta pra uma nota),
  isso REATRIBUÍA a tag para a nota nova e a removia silenciosamente
  da nota antiga que também usava aquele nome.

  Como o front-end só usa "tags" como uma lista de strings por nota
  (não existe tela de editar/renomear uma tag isoladamente — ver
  left-panel.ts, que só lê "tag.name"), não há necessidade de uma
  entidade relacional própria. Aqui as tags são só uma lista de
  strings pertencente à nota, sem esse risco de reatribuição.
*/
@Getter
@Setter
@Entity
@Table(name = "nota", schema = "notes")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    // Guarda a imagem exatamente como o front-end envia: uma Data URL
    // em base64 (ver note.ts do Angular). Sem upload de arquivo nem
    // dependência de armazenamento externo (S3, etc.).
    @Column(name = "imagem", columnDefinition = "TEXT")
    private String imagem;

    @ElementCollection
    @CollectionTable(name = "nota_tag", schema = "notes", joinColumns = @JoinColumn(name = "id_nota"))
    @Column(name = "nome", nullable = false)
    private List<String> tags = new ArrayList<>();

    @Column(name = "arquivado", nullable = false)
    private Boolean arquivado = false;

    @Column(name = "data_criacao")
    private OffsetDateTime dataCriacao;

    @Column(name = "ultima_edicao")
    private OffsetDateTime ultimaEdicao;
}
