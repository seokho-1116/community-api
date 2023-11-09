package com.example.community.repository.jpa;

import static org.assertj.core.api.Assertions.*;

import com.example.community.repository.MemberJpaRepository;
import com.example.community.service.entity.Member;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberJpaRepositoryTest extends JpaRepositoryTest {
  private final EntityManager em;
  private final MemberJpaRepository memberJpaRepository;

  @Autowired
  public MemberJpaRepositoryTest(EntityManager entityManager) {
    this.em = entityManager;
    this.memberJpaRepository = new MemberJpaRepository(entityManager);
  }

  @Test
  void findMemberByPublicId() {
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());

    Member member = memberJpaRepository.findMemberByPublicId(publicId);

    assertThat(member).isNotNull();
  }

  @Test
  void updateNickname() {
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());
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
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());
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
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());
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