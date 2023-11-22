package com.example.community.repository;

import com.example.community.service.entity.Comment;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepository {
  private final EntityManager em;

  public void save(Comment entity) {
    em.persist(entity);
  }

  public Optional<Comment> findCommentByBoardPublicIdAndPostPublicIdAndPublicId(UUID boardPublicId,
      UUID postPublicId, UUID commentPublicId) {
    TypedQuery<Comment> query = em.createQuery("select c from Comment c "
        + "where c.boardPublicId=:boardPublicId "
        + "and c.publicId=:commentPublicId and c.postPublicId=:postPublicId", Comment.class);

    query.setParameter("boardPublicId", boardPublicId);
    query.setParameter("commentPublicId", commentPublicId);
    query.setParameter("postPublicId", postPublicId);

    return query.getResultStream().findFirst();
  }

  public void remove(Comment comment) {
    em.remove(comment);
  }
}
