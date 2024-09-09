package org.example.domain.workbook.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.enums.Role;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.problem.Level;
import org.example.domain.study_member.repository.DetailStudyMemberRepository;
import org.example.domain.workbook.Workbook;
import org.example.domain.workbook.controller.response.DetailWorkbookResponse;
import org.example.domain.workbook_problem.controller.response.ListWorkbookProblemDto;
import org.example.domain.workbook_problem.repository.ListWorkbookProblemRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailWorkbookService {

  private final CoreMemberService coreMemberService;
  private final CoreWorkbookService coreWorkbookService;
  private final ListWorkbookProblemRepository listWorkbookProblemRepository;
  private final DetailStudyMemberRepository detailStudyMemberRepository;

  /**
   * 문제집 상세 조회
   */
  public DetailWorkbookResponse getWorkbook(Long workbookId) {
    if (!detailStudyMemberRepository.isRegularStudyMember()
    && coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()).getRole().equals(Role.ROLE_USER)) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "스터디원만 열람할 수 있습니다.");
    }

    Workbook workbook = coreWorkbookService.findById(workbookId);
    List<ListWorkbookProblemDto> problemList = listWorkbookProblemRepository.getWorkbookProblemList(workbookId);
    for (ListWorkbookProblemDto problem : problemList) {
      problem.setLevelUrl(Level.valueOf(problem.getLevelUrl()));
    }

    return DetailWorkbookResponse.builder()
      .name(workbook.getName())
      .problemList(problemList)
      .build();
  }
}
