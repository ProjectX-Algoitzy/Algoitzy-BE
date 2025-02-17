package org.example.domain.workbook.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.problem.Level;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.workbook.controller.response.ListWorkbookDto;
import org.example.domain.workbook.controller.response.ListWorkbookResponse;
import org.example.domain.workbook.repository.ListWorkbookRepository;
import org.example.domain.workbook_problem.controller.response.ListWorkbookProblemDto;
import org.example.domain.workbook_problem.repository.ListWorkbookProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListWorkbookService {

  private final CoreStudyService coreStudyService;
  private final ListWorkbookRepository listWorkbookRepository;
  private final ListWorkbookProblemRepository listWorkbookProblemRepository;

  /**
   * 정규 스터디 모의테스트 조회
   */
  public ListWorkbookResponse getWorkbookList(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디가 아닙니다.");
    }

    List<ListWorkbookDto> workbookList = listWorkbookRepository.getWorkbookList(study);
    for (ListWorkbookDto workbook : workbookList) {
      workbook.getProblemList().addAll(listWorkbookProblemRepository.getWorkbookProblemList(workbook.getWorkbookId()));
      // enum -> 티어 이미지 변환
      for (ListWorkbookProblemDto problem : workbook.getProblemList()) {
        problem.setLevelUrl(Level.valueOf(problem.getLevelUrl()));
      }
    }

    return ListWorkbookResponse.builder()
      .workbookList(workbookList)
      .build();
  }

}
