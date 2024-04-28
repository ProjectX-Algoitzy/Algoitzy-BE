package org.example.domain.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.repository.CoreDetailApplicationRepository;
import org.example.domain.application.response.DetailApplicationResponse;
import org.example.domain.select_question.response.DetailSelectQuestionDto;
import org.example.domain.select_question.service.CoreListSelectQuestionService;
import org.example.domain.text_question.response.DetailTextQuestionDto;
import org.example.domain.text_question.service.CoreListTextQuestionService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreDetailApplicationService {

  private final CoreDetailApplicationRepository coreDetailApplicationRepository;
  private final CoreListSelectQuestionService coreListSelectQuestionService;
  private final CoreListTextQuestionService coreListTextQuestionService;

  /**
   * 지원서 양식 상세 조회
   */
  public DetailApplicationResponse getApplication(Long applicationId) {
    DetailApplicationResponse response = coreDetailApplicationRepository.getApplication(applicationId);
    List<DetailSelectQuestionDto> selectQuestionList = coreListSelectQuestionService.getSelectQuestionList(applicationId);
    List<DetailTextQuestionDto> textQuestionList = coreListTextQuestionService.getTextQuestionList(applicationId);

    return response.toBuilder()
      .selectQuestionList(selectQuestionList)
      .textQuestionList(textQuestionList)
      .build();
  }
}
