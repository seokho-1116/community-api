package com.example.community.repository;

import com.example.community.service.entity.Comment;
import com.example.community.service.entity.Post;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostJpaRepository {
  private final EntityManager em;

  public Post save(Post p) {
    em.persist(p);

    return p;
  }

  public Post findPostByBoardPublicIdAndPublicId(UUID boardPublicId, UUID postPublicId) {
    TypedQuery<Post> query = em.createQuery("select p from Post p "
        + "where p.publicId=:postPublicId and p.boardPublicId=:boardPublicId", Post.class);

    query.setParameter("postPublicId", postPublicId);
    query.setParameter("boardPublicId", boardPublicId);

    return query.getSingleResult();
  }

  public void remove(Post post) {
    em.remove(post);
  }
}
