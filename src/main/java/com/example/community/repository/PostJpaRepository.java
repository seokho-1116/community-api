package com.example.community.repository;

import com.example.community.service.entity.Post;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostJpaRepository {
  private final EntityManager em;

  public void save(Post p) {
    em.persist(p);
  }

  public Optional<Post> findPostByBoardPublicIdAndPublicId(UUID boardPublicId, UUID postPublicId) {
    TypedQuery<Post> query = em.createQuery("select p from Post p "
        + "where p.publicId=:postPublicId and p.boardPublicId=:boardPublicId", Post.class);

    query.setParameter("postPublicId", postPublicId);
    query.setParameter("boardPublicId", boardPublicId);

    return query.getResultStream().findFirst();
  }

  public void remove(Post post) {
    em.remove(post);
  }
}
