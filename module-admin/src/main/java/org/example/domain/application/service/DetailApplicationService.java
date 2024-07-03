package org.example.domain.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.application.controller.response.DetailApplicationResponse;
import org.example.domain.application.repository.DetailApplicationRepository;
import org.example.domain.select_question.controller.response.DetailSelectQuestionDto;
import org.example.domain.select_question.service.ListSelectQuestionService;
import org.example.domain.text_question.controller.response.DetailTextQuestionDto;
import org.example.domain.text_question.service.ListTextQuestionService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailApplicationService {

  private final DetailApplicationRepository detailApplicationRepository;
  private final ListSelectQuestionService listSelectQuestionService;
  private final ListTextQuestionService listTextQuestionService;

  /**
   * 지원서 양식 상세 조회
   */
  public DetailApplicationResponse getApplication(Long applicationId) {
    DetailApplicationResponse response = detailApplicationRepository.getApplication(applicationId);
    if (response == null) {
      throw new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 지원서 ID입니다.");
    }
    List<DetailSelectQuestionDto> selectQuestionList = listSelectQuestionService.getSelectQuestionList(applicationId);
    List<DetailTextQuestionDto> textQuestionList = listTextQuestionService.getTextQuestionList(applicationId);

    return response.toBuilder()
      .selectQuestionList(selectQuestionList)
      .textQuestionList(textQuestionList)
      .build();
  }
}
