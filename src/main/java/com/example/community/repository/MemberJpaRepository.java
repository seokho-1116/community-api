package com.example.community.repository;

import com.example.community.service.entity.Member;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {
  private final EntityManager em;

  public void save(Member entity) {
    em.persist(entity);
  }

  public Member findMemberByPublicId(UUID memberPublicId) {
    TypedQuery<Member> query = em.createQuery(
        "select m from Member m where m.publicId=:memberPublicId", Member.class);

    query.setParameter("memberPublicId", memberPublicId);

    return query.getSingleResult();
  }
}
