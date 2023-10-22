package com.example.community.repository;

import com.example.community.service.entity.Comment;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepository {
  private final EntityManager em;

  public Comment save(Comment entity) {
    em.persist(entity);

    return entity;
  }

  public Comment findByBoardPublicIdAndPostPublicIdAndPublicId(UUID boardPublicId, UUID postPublicId,
      UUID commentPublicId) {
    TypedQuery<Comment> query = em.createQuery("select c from Comment c "
        + "where c.boardPublicId=:boardPublicId "
        + "and c.publicId=:commentPublicId and c.postPublicId=:postPublicId", Comment.class);

    query.setParameter("boardPublicId", boardPublicId);
    query.setParameter("commentPublicId", commentPublicId);
    query.setParameter("postPublicId", postPublicId);

    return query.getSingleResult();
  }

  public void remove(Comment comment) {
    em.remove(comment);
  }
}
