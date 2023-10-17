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

  public String updateNickname(UUID memberId, String nickname) {
    Member member = getMember(memberId);

    member.changeNickname(nickname);

    return nickname;
  }

  private Member getMember(UUID memberId) {
    TypedQuery<Member> query = em.createQuery(
        "select m from Member m where m.publicId=:memberId", Member.class);

    query.setParameter("memberId", memberId);

    return query.getSingleResult();
  }

  public String updateEmail(UUID memberId, String email) {
    Member member = getMember(memberId);

    member.changeEmail(email);

    return email;
  }

  public void updatePassword(UUID memberId, String password) {
    Member member = getMember(memberId);

    member.changePassword(password);
  }
}
