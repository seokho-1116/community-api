package com.example.community.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.community.service.entity.Member;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberJpaRepositoryTest {
  private final EntityManager em;
  private final MemberJpaRepository memberJpaRepository;

  @Autowired
  public MemberJpaRepositoryTest(EntityManager entityManager) {
    this.em = entityManager;
    this.memberJpaRepository = new MemberJpaRepository(entityManager);
  }

  @Test
  void updateNickname() {
    UUID publicId = UUID.fromString("cfd8be94-42d9-49b4-b021-5b0da1945c5a");
    String nickname = "new";

    memberJpaRepository.updateNickname(publicId, nickname);
    em.flush();

    Member member = getMember(publicId);

    assertThat(nickname).isEqualTo(member.getNickname());
  }

  private Member getMember(UUID memberId) {
    TypedQuery<Member> query = em.createQuery(
        "select m from Member m where m.publicId=:memberId", Member.class);

    query.setParameter("memberId", memberId);

    return query.getSingleResult();
  }

  @Test
  void updateEmail() {
    UUID publicId = UUID.fromString("cfd8be94-42d9-49b4-b021-5b0da1945c5a");
    String email = "new";

    memberJpaRepository.updateEmail(publicId, email);
    em.flush();

    Member member = getMember(publicId);

    assertThat(email).isEqualTo(member.getEmail());
  }

  @Test
  void updatePassword() {
    UUID publicId = UUID.fromString("cfd8be94-42d9-49b4-b021-5b0da1945c5a");
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    String password = passwordEncoder.encode("new");

    memberJpaRepository.updatePassword(publicId, password);
    em.flush();

    Member member = getMember(publicId);

    assertThat(password).isEqualTo(member.getSignupPassword());
  }
}