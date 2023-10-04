package com.example.community.repository;

import com.example.community.service.entity.Post;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostJpaRepository {
  private final EntityManager em;

  @Transactional
  public Post save(Post p) {
    em.persist(p);

    return p;
  }
}
