package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.generation.repository.GenerationRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.s3_file.service.CoreS3FileService;
import org.example.domain.study.Study;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.controller.request.UpdateStudyRequest;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.StudyRepository;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.DetailStudyMemberRepository;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.domain.study_member.service.CoreStudyMemberService;
import org.example.email.service.CoreEmailService;
import org.example.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyService {

  private final CoreStudyMemberService coreStudyMemberService;
  private final CoreStudyService coreStudyService;
  private final CoreS3FileService coreS3FileService;
  private final CoreMemberService coreMemberService;
  private final CoreEmailService coreEmailService;
  private final DetailStudyMemberRepository detailStudyMemberRepository;
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

  /**
   * 자율 스터디 수정
   */
  public void updateStudy(Long studyId, UpdateStudyRequest request) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디는 수정할 수 없습니다.");
    }

    String profileUrl = (StringUtils.hasText(request.profileUrl())) ? request.profileUrl() : basicStudyImage;
    study.update(profileUrl, request.name(), request.content());
  }

  /**
   * 자율 스터디 종료
   */
  public void endTempStudy(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디는 종료할 수 없습니다.");
    }

    StudyMember leader = detailStudyMemberRepository.getTempStudyLeader(studyId);
    if (!leader.getMember().getEmail().equals(SecurityUtils.getCurrentMemberEmail())) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "스터디장만 접근 가능합니다.");
    }

    study.end();
  }

  /**
   * 자율 스터디 지원
   */
  public void applyTempStudy(Long studyId) {
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    Study study = coreStudyService.findById(studyId);
    if (study.getEndYN()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "종료된 스터디입니다.");
    }

    if (studyMemberRepository.findByStudyAndMember(study, member).isPresent()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "이미 지원한 스터디입니다.");
    }

    if (study.getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "자율 스터디가 아닙니다.");
    }

    studyMemberRepository.save(
      StudyMember.builder()
        .study(study)
        .member(member)
        .role(StudyMemberRole.MEMBER)
        .status(StudyMemberStatus.TEMP_APPLY)
        .build()
    );

    // 스터디장에게 메일 발송
    StudyMember leader = detailStudyMemberRepository.getTempStudyLeader(studyId);
    coreEmailService.sendTempApplyEmail(leader);
  }

  /**
   * 자율 스터디원 수락
   */
  public void passTempStudy(Long studyMemberId) {
    StudyMember studyMember = coreStudyMemberService.findById(studyMemberId);
    if (studyMember.getStudy().getEndYN()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "종료된 스터디입니다.");
    }
    if (studyMember.getStudy().getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "자율 스터디가 아닙니다.");
    }
    if (studyMember.getStatus().equals(StudyMemberStatus.PASS)) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "이미 수락한 지원자입니다.");
    }

    StudyMember leader = detailStudyMemberRepository.getTempStudyLeader(studyMember.getStudy().getId());
    if (!leader.getMember().getEmail().equals(SecurityUtils.getCurrentMemberEmail())) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "스터디장만 접근 가능합니다.");
    }

    studyMember.updateStatus(StudyMemberStatus.PASS);

    // 지원자에게 메일 발송
    coreEmailService.sendTempPassEmail(studyMember);
  }
}
