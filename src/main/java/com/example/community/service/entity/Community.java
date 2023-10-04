package com.example.community.service.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "community")
public class Community {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "introduction")
  private String introduction;

  @Column(name = "posts_count")
  private Long postsCount;

  @Column(name = "members_count")
  private Long membersCount;

  @Column(name = "contact_info")
  private String contactInfo;

  @Column(name = "privacy_policy")
  private String privacyPolicy;

  @Column(name = "terms")
  private String terms;

  @Column(name = "ads_info")
  private String adsInfo;

  @Column(name = "company_info")
  private String companyInfo;
}