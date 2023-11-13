package com.example.community.repository;

import com.example.community.service.entity.Token;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenJpaRepository {
  private final EntityManager em;

  public Optional<Token> findTokenByPublicId(UUID publicId) {
    TypedQuery<Token> query = em.createQuery("select t from Token t "
        + "where t.publicId = :publicId", Token.class);

    query.setParameter("publicId", publicId);

    return query.getResultStream().findFirst();
  }

  public void save(Token token) {
    em.persist(token);
  }

  public void remove(Token token) {
    em.remove(token);
  }
}
