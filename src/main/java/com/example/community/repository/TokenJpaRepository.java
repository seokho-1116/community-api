package com.example.community.repository;

import com.example.community.service.entity.Token;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TokenJpaRepository {
  private final EntityManager entityManager;

  @Transactional
  public Token save(Token token) {
    entityManager.persist(token);
    return token;
  }
}
