package org.example.domain.study_member.service;


import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberDto;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.study_member.repository.ListStudyMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListStudyMemberService {

  private final CoreStudyService coreStudyService;
  private final ListStudyMemberRepository listStudyMemberRepository;

  /**
   * 정규 스터디 스터디원 추가 대상 목록 조회
   */
  public ListMemberResponse getNonStudyMemberList(Long studyId, SearchMemberRequest request) {
    Study study = coreStudyService.findById(studyId);
    if (!study.getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디가 아닙니다.");
    }

    if (study.getEndYN()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "종료된 스터디입니다.");
    }

    Page<ListMemberDto> page = listStudyMemberRepository.getNonStudyMemberList(studyId, request);

    return ListMemberResponse.builder()
      .memberList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
