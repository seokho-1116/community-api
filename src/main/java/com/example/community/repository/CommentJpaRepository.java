package com.example.community.repository;

import com.example.community.service.entity.Comment;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepository {
  private final EntityManager em;

  @Transactional
  public Comment save(Comment entity) {
    em.persist(entity);

    return entity;
  }
}
