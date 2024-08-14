package org.example.domain.text_answer.service;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.answer.Answer;
import org.example.domain.text_answer.TextAnswer;
import org.example.domain.text_answer.controller.request.CreateTextAnswerRequest;
import org.example.domain.text_answer.repository.TextAnswerRepository;
import org.example.domain.text_question.TextQuestion;
import org.example.domain.text_question.repository.TextQuestionRepository;
import org.example.domain.text_question.service.CoreTextQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTextAnswerService {

  private final CoreTextQuestionService coreTextQuestionService;
  private final TextAnswerRepository textAnswerRepository;
  private final TextQuestionRepository textQuestionRepository;

  /**
   * 주관식 답변 생성
   */
  public void createTextAnswer(Answer answer, List<CreateTextAnswerRequest> requestList) {

    // 모든 필수 문항 응답 여부 확인
    if (answer.getConfirmYN()) {
      List<Long> requiredTextQuestionIdList =
        textQuestionRepository.findAllByApplicationAndIsRequiredIsTrue(answer.getApplication())
          .stream().map(TextQuestion::getId).toList();
      List<Long> requestTextQuestionIdList = requestList.stream().map(CreateTextAnswerRequest::textQuestionId).toList();
      List<String> requestTextQuestionTextList = requestList.stream().map(CreateTextAnswerRequest::text).toList();
      if (requestTextQuestionTextList.stream().anyMatch(text -> !StringUtils.hasText(text))) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "주관식 필수 문항에 응답해주세요.");
      }
      boolean allRequiredAnswered = new HashSet<>(requestTextQuestionIdList).containsAll(requiredTextQuestionIdList);
      if (!allRequiredAnswered) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "주관식 필수 문항에 응답해주세요.");
      }
    }

    List<TextAnswer> textAnswerList = requestList.stream()
      .map(request -> textAnswerRepository.save(
        TextAnswer.builder()
          .answer(answer)
          .textQuestion(coreTextQuestionService.findById(request.textQuestionId()))
          .text(request.text())
          .build())
      ).toList();

    answer.setTextAnswerList(textAnswerList);
  }
}
