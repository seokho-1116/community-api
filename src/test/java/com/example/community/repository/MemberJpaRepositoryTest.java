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
    UUID publicId = UUID.fromString("7da7aa39-d829-43ac-a3f1-b770d8deff1e");
    String nickname = "new";

    Member entity = memberJpaRepository.findMemberByPublicId(publicId);
    entity.changeNickname(nickname);
    em.flush();
    em.clear();

    Member member = memberJpaRepository.findMemberByPublicId(publicId);

    assertThat(nickname).isEqualTo(member.getNickname());
  }

  @Test
  void updateEmail() {
    UUID publicId = UUID.fromString("7da7aa39-d829-43ac-a3f1-b770d8deff1e");
    String email = "new";

    Member entity = memberJpaRepository.findMemberByPublicId(publicId);
    entity.changeEmail(email);
    em.flush();
    em.clear();

    Member member = memberJpaRepository.findMemberByPublicId(publicId);

    assertThat(email).isEqualTo(member.getEmail());
  }

  @Test
  void updatePassword() {
    UUID publicId = UUID.fromString("7da7aa39-d829-43ac-a3f1-b770d8deff1e");
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    String password = passwordEncoder.encode("new");

    Member entity = memberJpaRepository.findMemberByPublicId(publicId);
    entity.changePassword(password);
    em.flush();
    em.clear();

    Member member = memberJpaRepository.findMemberByPublicId(publicId);

    assertThat(password).isEqualTo(member.getSignupPassword());
  }
}