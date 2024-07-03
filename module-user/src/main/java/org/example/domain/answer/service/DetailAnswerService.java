package org.example.domain.answer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.answer.Answer;
import org.example.domain.answer.controller.response.DetailAnswerResponse;
import org.example.domain.answer.repository.DetailAnswerRepository;
import org.example.domain.select_answer.response.DetailSelectAnswerDto;
import org.example.domain.select_answer.service.ListSelectAnswerService;
import org.example.domain.text_answer.response.DetailTextAnswerDto;
import org.example.domain.text_answer.service.ListTextAnswerService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailAnswerService {

  private final DetailAnswerRepository detailAnswerRepository;
  private final ListSelectAnswerService listSelectAnswerService;
  private final ListTextAnswerService listTextAnswerService;
  private final CoreAnswerService coreAnswerService;

  /**
   * 작성한 지원서 상세 조회
   */
  public DetailAnswerResponse getAnswer(Long answerId) {
    Answer answer = coreAnswerService.findById(answerId);
    if (!SecurityUtils.getCurrentMemberEmail().equals(answer.getCreatedBy())) {
      throw new GeneralException(ErrorStatus.UNAUTHORIZED, "타인의 지원서는 열람할 수 없습니다.");
    }

    DetailAnswerResponse response = detailAnswerRepository.getAnswer(answerId);
    List<DetailSelectAnswerDto> selectAnswerList = listSelectAnswerService.getSelectAnswerList(answer);
    List<DetailTextAnswerDto> textAnswerList = listTextAnswerService.getTextAnswerList(answerId);

    return response.toBuilder()
      .selectAnswerList(selectAnswerList)
      .textAnswerList(textAnswerList)
      .build();
  }
}
