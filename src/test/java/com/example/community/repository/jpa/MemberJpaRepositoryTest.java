package com.example.community.repository.jpa;

import static org.assertj.core.api.Assertions.*;

import com.example.community.repository.MemberJpaRepository;
import com.example.community.service.entity.Member;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
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

  @DisplayName("공개_키로_멤버_엔티티_조회_쿼리_테스트")
  @Test
  void findMemberByPublicId() {
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());

    Member member = memberJpaRepository.findMemberByPublicId(publicId)
        .orElseThrow();

    assertThat(member).isNotNull();
  }

  @DisplayName("멤버_닉네임_업데이트_쿼리_테스트")
  @Test
  void updateNickname() {
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());
    String nickname = "new";

    Member entity = memberJpaRepository.findMemberByPublicId(publicId)
        .orElseThrow();
    entity.changeNickname(nickname);
    em.flush();
    em.clear();

    Member member = memberJpaRepository.findMemberByPublicId(publicId)
        .orElseThrow();

    assertThat(nickname).isEqualTo(member.getNickname());
  }

  @DisplayName("멤버_이메일_업데이트_쿼리_테스트")
  @Test
  void updateEmail() {
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());
    String email = "new";

    Member entity = memberJpaRepository.findMemberByPublicId(publicId)
        .orElseThrow();
    entity.changeEmail(email);
    em.flush();
    em.clear();

    Member member = memberJpaRepository.findMemberByPublicId(publicId)
        .orElseThrow();

    assertThat(email).isEqualTo(member.getEmail());
  }

  @DisplayName("멤버_비밀번호_업데이트_쿼리_테스트")
  @Test
  void updatePassword() {
    UUID publicId = UUID.fromString(TEST_DATA.getMemberPublicId());
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    String password = passwordEncoder.encode("new");

    Member entity = memberJpaRepository.findMemberByPublicId(publicId)
        .orElseThrow();
    entity.changePassword(password);
    em.flush();
    em.clear();

    Member member = memberJpaRepository.findMemberByPublicId(publicId)
        .orElseThrow();

    assertThat(password).isEqualTo(member.getSignupPassword());
  }
}