package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.generation.repository.GenerationRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.s3_file.service.CoreS3FileService;
import org.example.domain.study.Study;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.StudyRepository;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyService {

  private final CoreS3FileService coreS3FileService;
  private final CoreMemberService coreMemberService;
  private final StudyRepository studyRepository;
  private final StudyMemberRepository studyMemberRepository;
  private final GenerationRepository generationRepository;

  @Value("${s3.basic-image.study}")
  private String basicStudyImage;

  /**
   * 자율 스터디 생성
   */
  public void createTempStudy(CreateTempStudyRequest request) {
    if (StringUtils.hasText(request.profileUrl())) {
      coreS3FileService.findByFileUrl(request.profileUrl());
    }

    Study study = studyRepository.save(
      Study.builder()
        .profileUrl(StringUtils.hasText(request.profileUrl()) ? request.profileUrl() : basicStudyImage)
        .name(request.name())
        .content(request.content())
        .type(StudyType.TEMP)
        .generation(generationRepository.findTopByOrderByValueDesc())
        .build()
    );

    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    studyMemberRepository.save(
      StudyMember.builder()
        .study(study)
        .member(member)
        .role(StudyMemberRole.LEADER)
        .status(StudyMemberStatus.PASS)
        .build()
    );

  }
}
